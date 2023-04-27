package com.example.notnullserver_semifinal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HeaderExample {
    private String messageNum;
    private long timestamp;
    private String sender;
    private String receiver;
    private String messageNumAnswer;
}
