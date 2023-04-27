package com.example.notnullserver_semifinal.authorisation.services.service;

import com.example.notnullserver_semifinal.authorisation.models.requestBodies.LogoutReq;
import com.example.notnullserver_semifinal.authorisation.models.responses.LogoutResponse;

public interface LogoutService {
    LogoutResponse quit(LogoutReq sessionId);
}
