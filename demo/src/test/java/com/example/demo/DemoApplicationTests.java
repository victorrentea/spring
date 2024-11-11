package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
	@Value("${config.x}")
	private Integer x;
	@Test
	void contextLoads() {
		System.out.println(x);
	}
}
