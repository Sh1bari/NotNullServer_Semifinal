package com.example.notnullserver_semifinal.webSocket.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.messaging.MessageHeaders;

@Getter
@Setter
@ToString
public class Message{
    private String message;

}
