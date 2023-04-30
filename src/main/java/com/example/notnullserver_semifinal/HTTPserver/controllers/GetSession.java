package com.example.notnullserver_semifinal.HTTPserver.controllers;

import com.example.notnullserver_semifinal.HTTPserver.models.SessionId;
import com.example.notnullserver_semifinal.threads.ThreadServiceBI;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class GetSession extends ThreadServiceBI {

    @PostMapping("/setSession")
    public void getSession(@RequestBody SessionId session){
        sessionId = session.getSession();
    }
}
