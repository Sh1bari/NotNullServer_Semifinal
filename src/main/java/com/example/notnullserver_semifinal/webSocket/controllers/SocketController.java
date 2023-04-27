package com.example.notnullserver_semifinal.webSocket.controllers;

import com.example.notnullserver_semifinal.webSocket.models.Message;
import com.example.notnullserver_semifinal.webSocket.services.service.MessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.example.notnullserver_semifinal.socket.MainServerSocket;

@CrossOrigin
@Controller
public class SocketController extends MainServerSocket {

    @Autowired
    private MessageSenderService messageSenderService;

    @MessageMapping("/messageForHandshake")
    @SendTo("/newHandshake")
    private String send(@Payload Message message) {
        System.out.println(handshakeConnectionMessage);
        String varHandshakeConnectionMessage = handshakeConnectionMessage;
        handshakeConnectionMessage = null;
        return "ghbdtn";
    }
}
