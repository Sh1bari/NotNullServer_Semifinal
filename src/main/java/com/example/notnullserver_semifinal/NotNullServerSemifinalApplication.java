package com.example.notnullserver_semifinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.example.notnullserver_semifinal.socket.threads.MainServerSocket;

@SpringBootApplication
public class NotNullServerSemifinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotNullServerSemifinalApplication.class, args);
	}

	@Bean(initMethod="mainSock")
	public MainServerSocket getMainSocketBean() {
		return new MainServerSocket();
	}

}
