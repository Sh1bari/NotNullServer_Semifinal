package com.example.notnullserver_semifinal.threads;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.ToString;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import ru.sovcombank.hackaton.proto.ExchangeInfoMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ThreadStatusCheck extends ThreadServiceBI{

    private Socket socket;
    private final SimpMessagingTemplate template;
    InputStream in = socket.getInputStream();
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
        Thread.sleep(100);
        while(socket.isConnected()){
            ExchangeInfoMessage msg = ExchangeInfoMessage.parseFrom(readAllBytes(socket));
            if(msg.hasResponse()){
                responseTimeout = false;
                template.convertAndSend("/connect/getStatus", msg);
                synchronized (objForStatusCloseSocket){
                    objForStatusCloseSocket.notifyAll();
                }
                break;
            }
        }
    }
}
