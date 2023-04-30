package com.example.notnullserver_semifinal.threads;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.ToString;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import ru.sovcombank.hackaton.proto.ExchangeInfoMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ThreadStatusCheck extends ThreadServiceBI{

    private static final Logger log =
            Logger.getLogger(ThreadStatusCheck.class.getName());

    private Socket socket;
    private final SimpMessagingTemplate template;
    private String name;
    public ThreadStatusCheck(SimpMessagingTemplate template, Socket socket, String name) throws IOException {
        this.name = name;
        this.template=template;
        this.socket = socket;
        start();
        responseTimeout = true;
    }

    @SneakyThrows
    @Override
    public void run() {
        new ThreadStatusClose(socket, name);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while(true){
            try {
                ExchangeInfoMessage msg = ExchangeInfoMessage.parseFrom(readAllBytes(socket));
                if (msg.hasResponse()) {
                    responseTimeout = false;
                    template.convertAndSend("/connect/getStatus", toJson(msg));
                    synchronized (objForStatusCloseSocket) {
                        objForStatusCloseSocket.notifyAll();
                    }
                    break;
                }
            }catch (InvalidProtocolBufferException | NullPointerException e) {
                log.info("Отключение клиента");
                break;
            }
        }
    }
}
