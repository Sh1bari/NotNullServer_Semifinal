package com.example.notnullserver_semifinal.authorisation.passwordEncoder;

public interface PasswordEncoder {

    String encode(CharSequence rawPassword);

    boolean matches(CharSequence rawPassword, String encodedPassword);
}
