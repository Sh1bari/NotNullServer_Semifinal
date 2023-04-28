package com.example.notnullserver_semifinal.threads;

import com.example.notnullserver_semifinal.threads.models.ServiceBI;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class ThreadCloseSocket extends MainServerSocket {
    private static final Logger log =
            Logger.getLogger(ThreadCloseSocket.class.getName());
    public Socket socket;

    public ThreadCloseSocket(Socket socket){
        this.socket = socket;
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
            }else {
                log.info("Новое подключение " + socket.getLocalAddress());
                serviceBIMap.put(handshakeConnectionMessage.getHeader().getReceiver(), socket);
            }
            timeout = true;
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}