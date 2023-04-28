package com.example.notnullserver_semifinal.HTTPserver.controllers;

import com.example.notnullserver_semifinal.HTTPserver.models.RequestMessage;
import com.example.notnullserver_semifinal.HTTPserver.services.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class RequestController {

    @Autowired
    private RequestService requestService;


    @PostMapping("/sendRequest")
    public String getResponse(@RequestBody RequestMessage request){
        return requestService.getResponse(request.getRequestMessage());
    }
}
