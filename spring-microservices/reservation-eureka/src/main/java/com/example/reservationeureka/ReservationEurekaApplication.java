package com.example.reservationeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ReservationEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationEurekaApplication.class, args);
	}

}
