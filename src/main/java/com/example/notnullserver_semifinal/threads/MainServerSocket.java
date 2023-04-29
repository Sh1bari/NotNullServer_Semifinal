package com.example.notnullserver_semifinal.threads;

import com.example.notnullserver_semifinal.threads.models.ServiceBI;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import com.example.notnullserver_semifinal.socket.services.serviceImpl.SocketImlp;
import ru.sovcombank.hackaton.proto.ExchangeInfoMessage;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class MainServerSocket extends SocketImlp {
    private static final Logger log =
            Logger.getLogger(MainServerSocket.class.getName());



    private Socket socket;

    public static boolean timeout = true;

    public static final Object objForClose = new Object();


    public static Map<String, Socket> serviceBIMap = new HashMap<>();

    public void mainSock(){
        start();
    }

    @SneakyThrows
    @Override
    public void run(){
        openMainSocket();
        while(true){
            socket = serverSocket.accept();
            try {
                new ThreadServiceBI(socket);
            }catch (Exception e){
                log.info("Не удалось подключиться к сервису");
                socket.close();
            }

        }
    }

}
