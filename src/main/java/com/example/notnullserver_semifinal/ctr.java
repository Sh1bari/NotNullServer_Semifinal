package com.example.notnullserver_semifinal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ctr {
    @GetMapping("/")
    public String hi(){
        return "hello";
    }
}
