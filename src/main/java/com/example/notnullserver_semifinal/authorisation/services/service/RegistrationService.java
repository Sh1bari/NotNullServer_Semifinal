package com.example.notnullserver_semifinal.authorisation.services.service;


import com.example.notnullserver_semifinal.authorisation.models.requestBodies.RegistrationReq;
import com.example.notnullserver_semifinal.authorisation.models.responses.RegistrationResponse;

public interface RegistrationService {
    RegistrationResponse createRegistrationCode(RegistrationReq user);

    void sendCode(String mail);

    RegistrationResponse mailConfirmation(RegistrationReq user);

}
