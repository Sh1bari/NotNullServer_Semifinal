package com.example.notnullserver_semifinal.threads;

import lombok.SneakyThrows;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.net.Socket;
import java.util.logging.Logger;

public class ThreadRequest extends ThreadServiceBI{

    private static final Logger log =
            Logger.getLogger(ThreadRequest.class.getName());

    private Socket socket;
    private final SimpMessagingTemplate template;

    private String name;
    public ThreadRequest(SimpMessagingTemplate template, Socket socket, String name){
        this.name = name;
        this.template=template;
        this.socket = socket;
        start();
        requestTimeout = true;
    }

    @SneakyThrows
    @Override
    public void run(){
        new ThreadRequestClose(socket, name, template);
        try{
            Thread.sleep(100);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

    }
}
