package com.example.notnullserver_semifinal.webSocket.controllers;

import com.example.notnullserver_semifinal.socket.threads.ThreadServiceBI;
import lombok.SneakyThrows;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@CrossOrigin
@Controller
@EnableWebSocketMessageBroker
@EnableWebSocket
public class HandshakeController extends ThreadServiceBI {

    private final SimpMessagingTemplate template;

    public HandshakeController(SimpMessagingTemplate template){
        start();
        this.template = template;
    }

    @SneakyThrows
    @Override
    public void run(){
        Map<Socket, String> varMap = new HashMap<>();
        if(!mapOfHandshakes.isEmpty()){
            varMap.putAll(mapOfHandshakes);
        }
        while (true){
            Thread.sleep(200);
            if(!Objects.equals(varMap, mapOfHandshakes)){
                template.convertAndSendToUser(sessionId, "/queue/newHandshake", mapOfHandshakes.values().toString());
                varMap.clear();
                varMap.putAll(mapOfHandshakes);
            }
        }
    }
}
