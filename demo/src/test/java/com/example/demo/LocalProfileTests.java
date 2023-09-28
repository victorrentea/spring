package com.example.demo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("local")
class LocalProfileTests {
	@Autowired
	ReservationRepo reservationRepo;
	@Test
	void contextLoads() {
		// din pachetul assertj
		Assertions.assertThat(reservationRepo.count()).isNotZero();
	}
}
