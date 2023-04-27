package com.example.notnullserver_semifinal.authorisation.models.responses;

import lombok.Data;

@Data
public class LoginResponse {
    private String status;

    private String sessionId;
}
