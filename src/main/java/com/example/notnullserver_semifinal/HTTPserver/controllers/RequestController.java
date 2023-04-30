package com.example.notnullserver_semifinal.HTTPserver.controllers;

import com.example.notnullserver_semifinal.HTTPserver.models.RequestMessage;
import com.example.notnullserver_semifinal.HTTPserver.services.service.RequestService;
import com.example.notnullserver_semifinal.threads.ThreadRequest;
import com.example.notnullserver_semifinal.threads.ThreadServiceBI;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import ru.sovcombank.hackaton.proto.ExchangeInfoMessage;

import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

@RestController
@CrossOrigin
public class RequestController extends ThreadServiceBI {

    @Autowired
    private SimpMessagingTemplate template;
    @SneakyThrows
    @PostMapping("/sendRequest")
    public void sendRequest(@RequestBody RequestMessage request){
        ExchangeInfoMessage.Builder msg = ExchangeInfoMessage.newBuilder().mergeFrom(fromJson(request.getMessage()));
        Socket socket = serviceBIMap.get(msg.getHeader().getReceiver());
        OutputStream out = socket.getOutputStream();
        out.write(msg.build().toByteArray());
        out.flush();
        new ThreadRequest(template, socket, msg.getHeader().getReceiver());
    }
}
