package com.example.notnullserver_semifinal.authorisation.repositories;

import com.example.notnullserver_semifinal.authorisation.models.entities.Session;
import org.springframework.data.repository.CrudRepository;

public interface SessionsRepo extends CrudRepository<Session, Integer> {

    boolean existsByUsername(String username);

    Session findByUsername(String username);

    Session findBySessionId(String sessionId);
}
