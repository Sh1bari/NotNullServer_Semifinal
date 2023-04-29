package com.example.notnullserver_semifinal.HTTPserver.controllers;

import com.example.notnullserver_semifinal.HTTPserver.models.RequestMessage;
import com.example.notnullserver_semifinal.HTTPserver.services.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@CrossOrigin
public class RequestController {

    @Autowired
    private RequestService requestService;


    /*@GetMapping("/null")
    public String getResponse(){
        List<String> list = new LinkedList<>();
        return list.toString();
    }*/
}
