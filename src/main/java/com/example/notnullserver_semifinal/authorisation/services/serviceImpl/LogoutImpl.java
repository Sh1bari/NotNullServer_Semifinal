package com.example.notnullserver_semifinal.authorisation.services.serviceImpl;

import com.example.notnullserver_semifinal.authorisation.models.entities.User;
import com.example.notnullserver_semifinal.authorisation.models.requestBodies.LogoutReq;
import com.example.notnullserver_semifinal.authorisation.models.responses.LogoutResponse;
import com.example.notnullserver_semifinal.authorisation.repositories.SessionsRepo;
import com.example.notnullserver_semifinal.authorisation.repositories.UsersRepo;
import com.example.notnullserver_semifinal.authorisation.services.service.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogoutImpl implements LogoutService {

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private SessionsRepo sessionsRepo;

    @Override
    public LogoutResponse quit(LogoutReq sessionId) {
        LogoutResponse response = new LogoutResponse();
        User user = usersRepo.findBySession(
                sessionsRepo.findBySessionId(
                        sessionId.getSessionId()
                )
        );
        user.setSession(null);
        usersRepo.save(user);
        response.setStatus("done");
        return response;
    }
}
