package com.example.notnullserver_semifinal.authorisation.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String mail;

    @NonNull
    private String username;

    @NonNull
    private String password;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Session session;

}
