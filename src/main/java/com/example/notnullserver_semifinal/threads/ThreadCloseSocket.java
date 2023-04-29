package com.example.notnullserver_semifinal.threads;

import com.example.notnullserver_semifinal.threads.models.ServiceBI;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class ThreadCloseSocket extends ThreadServiceBI {
    private static final Logger log =
            Logger.getLogger(ThreadCloseSocket.class.getName());

    public ThreadCloseSocket(){
        start();
    }

    @Override
    public void run(){
        try{
            synchronized (objForClose){
                objForClose.wait(5000, 0);
            }
            if(timeout){
                socket.close();
                log.info("Превышено время ожидания или ответ (ctHandshake) не поступил");
            }
            timeout = true;
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
