package com.example.notnullserver_semifinal.authorisation.models.requestBodies;

import lombok.Data;

@Data
public class LoginReq {
    private String username;

    private String password;
}
