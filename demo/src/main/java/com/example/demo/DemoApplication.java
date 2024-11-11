package com.example.demo;

import io.micrometer.core.aop.TimedAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

//@EnableCaching(order = 2)
@EnableConfigurationProperties(Config.class)
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean // definesc programatic beanuri
	@Order(1)
	public TimedAspect timedAspect() {
		TimedAspect timedAspect = new TimedAspect();
		return timedAspect;
	}

	@Bean
	@Order(2)
	public LoggerAspect loggedAspect() {
		return new LoggerAspect();
	}
}
