package com.example.notnullserver_semifinal.socket.services.threads;

import com.example.notnullserver_semifinal.socket.MainServerSocket;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ThreadReader extends MainServerSocket {

    private Socket socket;
    private InputStream in;

    public ThreadReader() throws IOException {
        start();
    }



}
