package com.example.notnullserver_semifinal.threads;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import ru.sovcombank.hackaton.proto.ExchangeInfoMessage;

import java.net.Socket;
import java.util.logging.Logger;

@Getter
@Setter
public class ThreadStatusClose extends ThreadServiceBI{

    private static final Logger log =
            Logger.getLogger(ThreadStatusClose.class.getName());
    private Socket socket;
    public ThreadStatusClose(Socket socket){
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
            ExchangeInfoMessage name = (ExchangeInfoMessage) fromJson(mapOfHandshakes.get(socket));
            String serviceName = name.getHeader().getSender();
            serviceBIMap.remove(serviceName);
            mapOfHandshakes.remove(socket);
            socket.close();
            log.info("Нет ответа от сервиса. Отключение...");
        }
        responseTimeout = true;
    }
}
