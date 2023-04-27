package com.example.notnullserver_semifinal.webSocket.controllers;

import com.example.notnullserver_semifinal.webSocket.models.Message;
import com.example.notnullserver_semifinal.webSocket.services.service.MessageSenderService;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.example.notnullserver_semifinal.socket.MainServerSocket;
import org.springframework.web.bind.annotation.RequestBody;
import ru.sovcombank.hackaton.proto.ExchangeInfoMessage;

import java.io.IOException;
import java.util.logging.Logger;

@CrossOrigin
@Controller
public class SocketController extends MainServerSocket {

    private static final Logger log =
            Logger.getLogger(SocketController.class.getName());

    @Autowired
    private MessageSenderService messageSenderService;

    @MessageMapping("/messageForHandshake")
    @SendTo("/connect/newHandshake")
    private String send(@RequestBody Message message){
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
