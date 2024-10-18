package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@EnableScheduling // makes @Scheduled work
@SpringBootApplication
@Import({AController.class,
		AnotherClass.class,
		GlobalExceptionHandler.class,
		AService.class, ARepository.class})
@ComponentScan(basePackages = "nopackage")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
