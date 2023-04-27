package com.example.notnullserver_semifinal.socket.services.serviceImpl;

import com.example.notnullserver_semifinal.socket.config.Config;
import com.example.notnullserver_semifinal.socket.services.service.SocketRepo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.net.ServerSocket;


@Service
@NoArgsConstructor
@Getter
@Setter
public class SocketImlp
        extends Thread
        implements SocketRepo {

    protected static ServerSocket serverSocket;

    @Override
    public void openMainSocket() throws Exception {
        serverSocket = new ServerSocket(Config.PORT);
    }

    @Override
    public void closeMainSocket() throws Exception {
        serverSocket.close();
    }


}
