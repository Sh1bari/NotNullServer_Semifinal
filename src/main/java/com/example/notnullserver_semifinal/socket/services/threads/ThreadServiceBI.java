package com.example.notnullserver_semifinal.socket.services.threads;

import com.example.notnullserver_semifinal.socket.MainServerSocket;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.util.JsonFormat;
import ru.sovcombank.hackaton.proto.ExchangeInfoMessage;
import ru.sovcombank.hackaton.proto.MessageEnumsProto;

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


    public static String toJson(MessageOrBuilder messageOrBuilder) throws IOException {
        return JsonFormat.printer().print(messageOrBuilder);
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
                handshakeConnectionMessage = toJson(exchangeInfoMessage);
                System.out.println(handshakeConnectionMessage);
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

}
