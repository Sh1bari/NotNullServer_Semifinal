package com.example.notnullserver_semifinal.threads.status;

import com.example.notnullserver_semifinal.threads.ThreadServiceBI;
import com.example.notnullserver_semifinal.threads.models.ErrorMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.SneakyThrows;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import ru.sovcombank.hackaton.proto.ExchangeInfoMessage;
import ru.sovcombank.hackaton.proto.MessageEnumsProto;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;


public class ThreadStatusCheck extends ThreadServiceBI {

    private static final Logger log =
            Logger.getLogger(ThreadStatusCheck.class.getName());

    private Socket socket;
    private final SimpMessagingTemplate template;
    private String name;

    public ThreadStatusCheck(SimpMessagingTemplate template, Socket socket, String name) throws IOException {
        this.name = name;
        this.template = template;
        this.socket = socket;
        start();
        responseTimeout = true;
    }

    @SneakyThrows
    @Override
    public void run() {
        new ThreadStatusClose(socket, name, template);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            try {
                ExchangeInfoMessage msg = ExchangeInfoMessage.parseFrom(readAllBytes(socket));
                /////
                if (msg.hasResponse() && (msg.getResponse().getCommand() == MessageEnumsProto.CommandType.ctStatus)) {
                    if (msg.getResponse().getAnswerType() == MessageEnumsProto.AnswerType.atAnswerOK) {
                        responseTimeout = false;
                        synchronized (objForStatusCloseSocket) {
                            objForStatusCloseSocket.notifyAll();
                        }
                        template.convertAndSendToUser(sessionId, "/queue/getStatus", toJson(msg));
                    } else if (msg.getResponse().getAnswerType() == MessageEnumsProto.AnswerType.atAnswerError) {
                        ErrorMessage error = new ErrorMessage();
                        error.setCommand("atAnswerError");
                        error.setErrorText(msg.getResponse().getErrorText());
                        template.convertAndSendToUser(sessionId, "/queue/errors", error);
                    } else if (msg.getResponse().getAnswerType() == MessageEnumsProto.AnswerType.atNotSupported) {
                        ErrorMessage error = new ErrorMessage();
                        error.setCommand("atAnswerError");
                        if (msg.getResponse().hasErrorText()) {
                            error.setErrorText(msg.getResponse().getErrorText());
                        }
                        template.convertAndSendToUser(sessionId, "/queue/errors", error);
                    }
                    break;
                }
                //////
                if (msg.hasEvent()) {
                    responseTimeout = false;
                    synchronized (objForStatusCloseSocket) {
                        objForStatusCloseSocket.notifyAll();
                    }
                    while (true) {
                        ExchangeInfoMessage event = ExchangeInfoMessage.parseFrom(readAllBytes(socket));
                        if (event.hasEvent()) {
                            template.convertAndSend("/connect/eventListener");
                        } else break;
                    }
                }
                break;
            } catch (InvalidProtocolBufferException | NullPointerException e) {
                log.info("Отключение клиента");
                break;
            }
        }
    }
}
