package com.example.notnullserver_semifinal.webSocket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin
@Controller
public class HandshakeController{

    @Autowired
    private SimpMessagingTemplate template;

    @SendTo("/connect/newHandshake")
    private String send(@RequestBody String message){
        return message;
    }
}
