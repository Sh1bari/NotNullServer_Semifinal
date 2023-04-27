package com.example.notnullserver_semifinal.authorisation.models.requestBodies;

import lombok.Data;

@Data
public class LogoutReq {
    private String sessionId;
}
