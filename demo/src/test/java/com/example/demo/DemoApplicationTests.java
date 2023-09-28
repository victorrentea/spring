package com.example.demo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
	@Autowired
	ReservationRepo reservationRepo;
	@Test
	void noDummyDataIsInserted() {
		Assertions.assertThat(reservationRepo.count()).isZero();
	}
}
