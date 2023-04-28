package com.example.notnullserver_semifinal.webSocket.services.serviceImpl;

import com.example.notnullserver_semifinal.threads.MainServerSocket;
import com.example.notnullserver_semifinal.webSocket.services.service.MessageSenderService;
import org.springframework.stereotype.Service;
import ru.sovcombank.hackaton.proto.ExchangeInfoMessage;

@Service
public class MessageSenderServiceImpl
        extends MainServerSocket
        implements MessageSenderService {
    @Override
    public String sendNewConnection() {
        ExchangeInfoMessage varHandshakeConnectionMessage = handshakeConnectionMessage;
        handshakeConnectionMessage = null;
        String answer = null;
        try {
            answer = toJson(varHandshakeConnectionMessage);
            System.out.println(answer);
        }catch (Exception e){
        }
        return answer;
    }
}
