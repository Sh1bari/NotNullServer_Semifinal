package com.example.notnullserver_semifinal.threads;

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
