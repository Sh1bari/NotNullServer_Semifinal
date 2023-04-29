package com.example.notnullserver_semifinal.webSocket.controllers;

import com.example.notnullserver_semifinal.threads.MainServerSocket;
import com.example.notnullserver_semifinal.threads.ThreadServiceBI;
import com.example.notnullserver_semifinal.webSocket.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;


@CrossOrigin
@Controller
@EnableWebSocketMessageBroker
@EnableWebSocket
public class HandshakeController extends ThreadServiceBI {

    @Autowired
    private SimpMessagingTemplate template;
    @MessageMapping("/subscribeToHandshakes")
    @SendTo("/connect/newHandshake")
    public String send(@Payload Message message){
        System.out.println(message);
        while (true){
            if(!listOfNewHandshakes.isEmpty()){
                template.convertAndSend("/connect/newHandshake", listOfNewHandshakes.get(0));
                listOfNewHandshakes.remove(0);
            }
        }
    }
}
