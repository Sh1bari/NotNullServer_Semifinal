package com.example.notnullserver_semifinal.authorisation.services.service;

import com.example.notnullserver_semifinal.authorisation.models.requestBodies.LoginReq;
import com.example.notnullserver_semifinal.authorisation.models.responses.LoginResponse;

public interface LoginService {
    LoginResponse send(LoginReq user);
}
