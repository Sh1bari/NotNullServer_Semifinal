package com.example.notnullserver_semifinal.threads;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import ru.sovcombank.hackaton.proto.ExchangeInfoMessage;

import java.net.Socket;
import java.util.logging.Logger;

public class ThreadStatusClose extends ThreadServiceBI{

    private static final Logger log =
            Logger.getLogger(ThreadStatusClose.class.getName());
    private Socket socket;
    private String name;
    private final SimpMessagingTemplate template;
    public ThreadStatusClose(Socket socket, String name, SimpMessagingTemplate template){
        this.template = template;
        this.name = name;
        this.socket = socket;
        start();
    }

    @SneakyThrows
    @Override
    public void run() {
        synchronized (objForStatusCloseSocket){
            objForStatusCloseSocket.wait(5000);
        }
        if (responseTimeout){
            serviceBIMap.remove(name);
            mapOfHandshakes.remove(socket);
            socket.close();

            template.convertAndSend("/errors");
            log.info("Нет ответа от сервиса. Отключение...");
        }
        responseTimeout = true;
    }
}
