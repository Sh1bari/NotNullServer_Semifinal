package com.example.notnullserver_semifinal.socket.threads.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ErrorMessage {
    private String command;
    private String errorText;
}
