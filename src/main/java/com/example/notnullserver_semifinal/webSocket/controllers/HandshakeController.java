package com.example.notnullserver_semifinal.webSocket.controllers;

import com.example.notnullserver_semifinal.webSocket.models.Message;
import com.example.notnullserver_semifinal.webSocket.services.service.MessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin
@Controller
public class HandshakeController{

    @Autowired
    private MessageSenderService messageSenderService;

    @MessageMapping("/messageForHandshake")
    @SendTo("/connect/newHandshake")
    private String send(@RequestBody Message message){
        return messageSenderService.sendNewConnection();
    }
}
