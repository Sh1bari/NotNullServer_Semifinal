package com.example.notnullserver_semifinal.socket.services.service;

import com.example.notnullserver_semifinal.socket.config.Config;

public interface SocketRepo extends Config {
    void openMainSocket() throws Exception;
    void closeMainSocket() throws Exception;
}
