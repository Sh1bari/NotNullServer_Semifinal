package com.example.notnullserver_semifinal.authorisation.mailSender.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Mail {
    @Id
    private String mail;

    private String code;

    private LocalTime time;
}
