package com.example.notnullserver_semifinal.threads;

import com.example.notnullserver_semifinal.models.ExchangeInfoMessageExample;
import com.example.notnullserver_semifinal.models.HeaderExample;
import com.example.notnullserver_semifinal.models.RequestExample;
import com.example.notnullserver_semifinal.socket.config.Config;
import ru.sovcombank.hackaton.proto.*;

import java.io.*;
import java.net.Socket;

import java.util.logging.Logger;

public class ThreadServiceBI extends MainServerSocket {

    private static final Logger log =
            Logger.getLogger(ThreadServiceBI.class.getName());

    protected static Socket socket;
    private OutputStream out;

    public static boolean requestTimeout = true;
    public static final Object objForRequestCloseSocket = new Object();
    public static boolean responseTimeout = true;
    public static final Object objForStatusCloseSocket = new Object();

    public static String sessionId;

    public ThreadServiceBI(Socket socket) throws IOException {
        ThreadServiceBI.socket = socket;
        out = socket.getOutputStream();
        start();
    }

    public ThreadServiceBI() {
    }


    @Override
    public void run(){
        handshakeConnect();
    }

    private void handshakeConnect(){
        try{
            new ThreadCloseSocket();
            Thread.sleep(100);
            byte[] handshake = readAllBytes(socket);
            ExchangeInfoMessage exchangeInfoMessage = ExchangeInfoMessage.parseFrom(handshake);
            if (exchangeInfoMessage.getRequest().getCommand() == MessageEnumsProto.CommandType.ctHandshake) {
                timeout = false;
                sendHandshakeResponse(exchangeInfoMessage);     //Ответ на handshake
                serviceBIMap.put(exchangeInfoMessage.getHeader().getSender(), socket);
                log.info("Новое подключение " + socket.getLocalAddress());
                mapOfHandshakes.put(socket, toJson(exchangeInfoMessage));
            }
            synchronized (objForClose){
                objForClose.notify();
            }

        } catch (IOException e) {
            log.info("Ошибка в дешифрации запроса/ответа");
        } catch (InterruptedException e) {
            log.info("Thread interrupted");
        }
    }

    private void sendHandshakeResponse(ExchangeInfoMessage exchangeInfoMessage){
        ExchangeInfoMessage handshakeResponse = ExchangeInfoMessage.newBuilder()
                .setHeader(Header.newBuilder()
                        .setMessageNum("999")
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
