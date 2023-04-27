package com.example.notnullserver_semifinal.socket.services.threads;

import com.example.notnullserver_semifinal.models.ExchangeInfoMessageExample;
import com.example.notnullserver_semifinal.models.HeaderExample;
import com.example.notnullserver_semifinal.models.RequestExample;
import com.example.notnullserver_semifinal.socket.MainServerSocket;
import com.example.notnullserver_semifinal.socket.config.Config;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.util.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sovcombank.hackaton.proto.*;

import java.io.*;
import java.net.Socket;

import java.util.logging.Logger;

public class ThreadServiceBI extends MainServerSocket {

    private static final Logger log =
            Logger.getLogger(ThreadServiceBI.class.getName());

    private Socket socket;
    private InputStream in;
    private OutputStream out;

    public ThreadServiceBI(Socket socket) throws IOException {
        this.socket = socket;
        in = socket.getInputStream();
        out = socket.getOutputStream();
        start();
    }

    @Override
    public void run(){
        handshakeConnect();
    }

    private void handshakeConnect(){
        try{
            new ThreadCloseSocket(socket);
            Thread.sleep(200);
            byte[] data = null;
            int length = -1;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            DataInputStream in1 = new DataInputStream(socket.getInputStream());
            while (true) {
                if ((length = in1.available()) > 0) {
                    data = new byte[length];
                    in1.readFully(data, 0, length);
                    outputStream.write(data, 0, length);
                    break;
                }
            }
            byte[] handshake = data;
            ExchangeInfoMessage exchangeInfoMessage = ExchangeInfoMessage.parseFrom(handshake);
            if (exchangeInfoMessage.getRequest().getCommand() == MessageEnumsProto.CommandType.ctHandshake) {
                timeout = false;
                handshakeConnectionMessage = exchangeInfoMessage;
                sendHandshakeResponse(exchangeInfoMessage);     //Ответ на handshake
            }
            synchronized (objForClose){
                objForClose.notify();
            }

        } catch (InvalidProtocolBufferException e) {
            log.info("Ошибка в дешифрации запроса/ответа");
        } catch (IOException e) {
            log.info("Ошибка в чтении запроса/ответа или ответ не поступил");
        } catch (InterruptedException e) {
            log.info("Thread interrupted");
        }
    }

    private ExchangeInfoMessageExample toExchangeInfoMessageExample(ExchangeInfoMessage message){
        ExchangeInfoMessageExample converted = new ExchangeInfoMessageExample();
        //
        HeaderExample headerExample = new HeaderExample();
        headerExample.setMessageNum(message.getHeader().getMessageNum());
        headerExample.setReceiver(message.getHeader().getReceiver());
        headerExample.setTimestamp(message.getHeader().getTimestamp());
        headerExample.setSender(message.getHeader().getSender());
        headerExample.setMessageNumAnswer(message.getHeader().getMessageNumAnswer());
        converted.setHeader(headerExample);
        //
        RequestExample requestExample = new RequestExample();
        //

        return converted;
    }

    private void sendHandshakeResponse(ExchangeInfoMessage exchangeInfoMessage){
        ExchangeInfoMessage handshakeResponse = ExchangeInfoMessage.newBuilder()
                .setHeader(Header.newBuilder()
                        .setMessageNum(exchangeInfoMessage.getHeader().getMessageNum()+1)
                        .setSender(Config.SERVER_NAME)
                        .setReceiver(exchangeInfoMessage.getHeader().getReceiver())
                        .setTimestamp(exchangeInfoMessage.getHeader().getTimestamp())
                        .setMessageNumAnswer(exchangeInfoMessage.getHeader().getMessageNum())
                        .build())
                .setResponse(Response.newBuilder()
                        .setAnswerType(MessageEnumsProto.AnswerType.atAnswerOK)
                        .setCommand(MessageEnumsProto.CommandType.ctHandshake)
                        .build())
                .build();
        try {
            out.write(handshakeResponse.toByteArray());
            out.flush();
        } catch (IOException e) {
            log.info("Не удалось отправить ответ");
        }
    }

}
