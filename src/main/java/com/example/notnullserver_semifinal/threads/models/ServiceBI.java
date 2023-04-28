package com.example.notnullserver_semifinal.threads.models;

import lombok.*;

import java.net.Socket;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ServiceBI {

    @NonNull
    public String name;
    @NonNull
    public Socket socket;
}
