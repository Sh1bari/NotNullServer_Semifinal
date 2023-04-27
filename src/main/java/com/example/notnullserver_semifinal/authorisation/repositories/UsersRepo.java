package com.example.notnullserver_semifinal.authorisation.repositories;

import com.example.notnullserver_semifinal.authorisation.models.entities.Session;
import com.example.notnullserver_semifinal.authorisation.models.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepo extends CrudRepository<User, Integer> {
    boolean existsByUsername(String username);
    User findByUsername(String username);

    User findBySession(Session session);

    boolean existsByMail(String mail);
}
