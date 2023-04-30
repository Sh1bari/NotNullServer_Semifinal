package com.example.notnullserver_semifinal.socket.threads.request;

import com.example.notnullserver_semifinal.socket.threads.ThreadServiceBI;
import com.example.notnullserver_semifinal.socket.threads.models.ErrorMessage;
import lombok.SneakyThrows;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import ru.sovcombank.hackaton.proto.ExchangeInfoMessage;
import ru.sovcombank.hackaton.proto.MessageEnumsProto;

import java.net.Socket;
import java.util.logging.Logger;

public class ThreadRequest extends ThreadServiceBI {

    private static final Logger log =
            Logger.getLogger(ThreadRequest.class.getName());

    private Socket socket;
    private final SimpMessagingTemplate template;

    private String name;

    public ThreadRequest(SimpMessagingTemplate template, Socket socket, String name) {
        this.name = name;
        this.template = template;
        this.socket = socket;
        start();
        requestTimeout = true;
    }

    @SneakyThrows
    @Override
    public void run() {
        new ThreadRequestClose(socket, name, template);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            ExchangeInfoMessage msg = ExchangeInfoMessage.parseFrom(readAllBytes(socket));
            if ((msg.hasResponse()) && (msg.getResponse().getCommand() == MessageEnumsProto.CommandType.ctExecCommand)) {
                requestTimeout = false;
                synchronized (objForRequestCloseSocket) {
                    objForRequestCloseSocket.notifyAll();
                }
                if (msg.getResponse().getAnswerType() == MessageEnumsProto.AnswerType.atAnswerOK) {
                    requestTimeout = false;
                    template.convertAndSendToUser(sessionId, "/queue/request", toJson(msg));
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
            } else if (msg.hasEvent()) {
                requestTimeout = false;
                synchronized (objForRequestCloseSocket) {
                    objForRequestCloseSocket.notifyAll();
                }
                while (true) {
                    ExchangeInfoMessage event = ExchangeInfoMessage.parseFrom(readAllBytes(socket));
                    if (event.hasEvent()) {
                        template.convertAndSend("/connect/eventListener");
                    } else break;
                }
            }
        }
    }


}
