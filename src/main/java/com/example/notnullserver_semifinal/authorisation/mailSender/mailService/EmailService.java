package com.example.notnullserver_semifinal.authorisation.mailSender.mailService;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
}
