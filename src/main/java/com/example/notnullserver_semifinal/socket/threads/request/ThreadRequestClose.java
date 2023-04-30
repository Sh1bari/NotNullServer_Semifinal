package com.example.notnullserver_semifinal.socket.threads.request;

import com.example.notnullserver_semifinal.socket.threads.ThreadServiceBI;
import com.example.notnullserver_semifinal.socket.threads.models.ErrorMessage;
import lombok.SneakyThrows;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.net.Socket;
import java.util.logging.Logger;

public class ThreadRequestClose extends ThreadServiceBI {

    private static final Logger log =
            Logger.getLogger(ThreadRequestClose.class.getName());

    private Socket socket;

    private String name;

    private final SimpMessagingTemplate template;

    public ThreadRequestClose(Socket socket, String name, SimpMessagingTemplate template) {
        this.template = template;
        this.name = name;
        this.socket = socket;
        start();
    }

    @SneakyThrows
    @Override
    public void run() {
        synchronized (objForRequestCloseSocket) {
            objForRequestCloseSocket.wait(5000);
        }
        if (requestTimeout) {
            serviceBIMap.remove(name);
            mapOfHandshakes.remove(socket);
            socket.close();
            ErrorMessage error = new ErrorMessage();
            error.setCommand("Service timeout");
            error.setErrorText(name + " не отвечает 5 секунд");
            template.convertAndSendToUser(sessionId, "/queue/errors", error);
            log.info("Нет ответа от сервиса " + name + ". Отключение...");
        }
        requestTimeout = true;
    }
}
