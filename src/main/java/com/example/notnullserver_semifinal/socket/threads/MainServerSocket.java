package com.example.notnullserver_semifinal.socket.threads;

import com.example.notnullserver_semifinal.webSocket.controllers.HandshakeController;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import com.example.notnullserver_semifinal.socket.services.serviceImpl.SocketImlp;

import java.net.Socket;
import java.util.HashMap;
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

    public static Map<Socket, String> mapOfHandshakes = new HashMap<>();

    @Autowired
    private SimpMessagingTemplate template;

    public void mainSock() {
        new HandshakeController(template);
        start();
    }


    @SneakyThrows
    @Override
    public void run() {
        try {
            openMainSocket();
        } catch (Exception e) {
            log.info("Не получилось открыть порт");
        }
        while (true) {
            socket = serverSocket.accept();
            try {
                new ThreadServiceBI(socket);
            } catch (Exception e) {
                log.info("Не удалось подключиться к сервису");
                socket.close();
            }

        }
    }

}
