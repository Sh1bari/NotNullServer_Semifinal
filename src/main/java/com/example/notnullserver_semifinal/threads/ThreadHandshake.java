package com.example.notnullserver_semifinal.threads;

import com.example.notnullserver_semifinal.webSocket.controllers.HandshakeController;

public class ThreadHandshake extends MainServerSocket{

    public ThreadHandshake(){
        start();
    }

    @Override
    public void run(){
        //HandshakeController handshakeController = new HandshakeController();
        //handshakeController.subscribe();
    }
}
