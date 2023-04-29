package com.example.notnullserver_semifinal.HTTPserver.controllers;

import com.example.notnullserver_semifinal.threads.MainServerSocket;
import com.example.notnullserver_semifinal.threads.ThreadServiceBI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class HandshakeRestController extends MainServerSocket {


    @GetMapping("/getCurHandshakes")
    public List<String> getHandshakes(){
        return mapOfHandshakes.values().stream().toList();
    }
}
