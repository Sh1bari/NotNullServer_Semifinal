package com.example.notnullserver_semifinal.socket.services.serviceImpl;

import com.example.notnullserver_semifinal.socket.config.Config;
import com.example.notnullserver_semifinal.socket.services.service.SocketRepo;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Struct;
import com.google.protobuf.util.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.sovcombank.hackaton.proto.ExchangeInfoMessage;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;


@Service
@NoArgsConstructor
@Getter
@Setter
public class SocketImlp
        extends Thread
        implements SocketRepo {

    private static final Logger log =
            Logger.getLogger(SocketImlp.class.getName());

    protected static ServerSocket serverSocket;

    @Override
    public void openMainSocket() throws Exception {
        serverSocket = new ServerSocket(Config.PORT);
    }

    @Override
    public void closeMainSocket() throws Exception {
        serverSocket.close();
    }

    public static String toJson(MessageOrBuilder messageOrBuilder) throws IOException {
        return JsonFormat.printer().includingDefaultValueFields().print(messageOrBuilder);
    }


    @SneakyThrows
    public static Message fromJson (String json){
        ExchangeInfoMessage.Builder structBuilder = ExchangeInfoMessage.newBuilder();
        JsonFormat.parser().ignoringUnknownFields().merge(json, structBuilder);
        return structBuilder.build();
    }

    public byte[] readAllBytes(Socket socket){
        byte[] data = null;
        int length = -1;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            DataInputStream in1 = new DataInputStream(socket.getInputStream());
            while (true) {
                if ((length = in1.available()) > 0) {
                    data = new byte[length];
                    in1.readFully(data, 0, length);
                    outputStream.write(data, 0, length);
                    break;
                }
            }
        } catch (IOException e) {
            log.info("Ошибка в чтении запроса/ответа или ответ не поступил");
        }
        return data;
    }


}
