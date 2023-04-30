package com.example.notnullserver_semifinal.HTTPserver.controllers;

import com.example.notnullserver_semifinal.HTTPserver.models.StatusModel;
import com.example.notnullserver_semifinal.threads.ThreadServiceBI;
import com.example.notnullserver_semifinal.threads.ThreadStatusCheck;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import ru.sovcombank.hackaton.proto.ExchangeInfoMessage;

@RestController
@CrossOrigin
public class StatusRestController extends ThreadServiceBI {

    @Autowired
    private SimpMessagingTemplate template;

    @SneakyThrows
    @PostMapping("/sendStatus")
    public void sendStatus(@RequestBody StatusModel model){

        ExchangeInfoMessage.Builder msg = ExchangeInfoMessage.newBuilder().mergeFrom(fromJson1(model.getMessage()));
        System.out.println(msg.getRequest().getCommand().name());
        System.out.println("выше");
        //Socket socket = serviceBIMap.get(model);
        //OutputStream out = socket.getOutputStream();
        //out.write(model);
        //out.flush();
        new ThreadStatusCheck(template, socket);
    }
}
