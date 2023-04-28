package com.example.notnullserver_semifinal.socket;

import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.util.JsonFormat;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import com.example.notnullserver_semifinal.socket.services.serviceImpl.SocketImlp;
import com.example.notnullserver_semifinal.socket.services.threads.ThreadServiceBI;
import ru.sovcombank.hackaton.proto.ExchangeInfoMessage;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

@Service
public class MainServerSocket extends SocketImlp {
    private static final Logger log =
            Logger.getLogger(MainServerSocket.class.getName());


    private Socket socket;

    public static boolean timeout = true;

    public static final Object objForClose = new Object();

    public static ExchangeInfoMessage handshakeConnectionMessage;

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
