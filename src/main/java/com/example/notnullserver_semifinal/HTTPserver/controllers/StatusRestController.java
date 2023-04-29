package com.example.notnullserver_semifinal.HTTPserver.controllers;

import com.example.notnullserver_semifinal.HTTPserver.models.StatusModel;
import com.example.notnullserver_semifinal.threads.MainServerSocket;
import com.example.notnullserver_semifinal.threads.ThreadStatusCheck;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import ru.sovcombank.hackaton.proto.ExchangeInfoMessage;

import java.io.OutputStream;
import java.net.Socket;

@RestController
@CrossOrigin
public class StatusRestController extends MainServerSocket {

    @Autowired
    private SimpMessagingTemplate template;

    @SneakyThrows
    @PostMapping("/sendStatus")
    public void sendStatus(@RequestBody StatusModel model){
        ExchangeInfoMessage msg = (ExchangeInfoMessage) fromJson(model.getMessage());
        Socket socket = serviceBIMap.get(msg.getHeader().getReceiver());
        OutputStream out = socket.getOutputStream();
        out.write(msg.toByteArray());
        out.flush();
        new ThreadStatusCheck(template, socket);
    }
}
