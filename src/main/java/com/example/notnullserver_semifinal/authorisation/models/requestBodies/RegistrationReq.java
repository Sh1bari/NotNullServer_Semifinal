package com.example.notnullserver_semifinal.authorisation.models.requestBodies;

import lombok.Data;

@Data
public class RegistrationReq {

    private String username;

    private String mail;

    private String password;

    private String code;
}
