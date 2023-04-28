package com.example.notnullserver_semifinal.HTTPserver.services.serviceImpl;

import com.example.notnullserver_semifinal.HTTPserver.services.service.RequestService;
import com.example.notnullserver_semifinal.threads.MainServerSocket;
import com.example.notnullserver_semifinal.webSocket.models.Message;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.sovcombank.hackaton.proto.ExchangeInfoMessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

@Service
public class RequestServiceImpl
        extends MainServerSocket
        implements RequestService{

    @SneakyThrows
    @Override
    public String getResponse(String request) {
        ExchangeInfoMessage msg = (ExchangeInfoMessage) fromJson(request);
        Socket socket = serviceBIMap.get(msg.getHeader().getReceiver());
        OutputStream out = socket.getOutputStream();
        ExchangeInfoMessage answer;
        try {
            out.write(msg.toByteArray());
            out.flush();
            answer = ExchangeInfoMessage.parseFrom(readAllBytes(socket));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return toJson(answer);
    }
}
