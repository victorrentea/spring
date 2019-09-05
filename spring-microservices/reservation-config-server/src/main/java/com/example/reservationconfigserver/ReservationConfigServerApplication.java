package com.example.reservationconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ReservationConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationConfigServerApplication.class, args);
	}

}
