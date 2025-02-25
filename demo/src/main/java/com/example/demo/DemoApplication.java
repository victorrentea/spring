package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@EventListener(ApplicationStartedEvent.class)
	void hello() {
		System.out.println("Hi");
	}

	@GetMapping
	String get() {
		return "REST";
	}
}

//define a bean that prints at startup "life has many aspects" only if there is a property called "intercept.methods" set to "true" >
//Tip: @ConditionalOnProperty+@Configuration class {@Bean method}

@Configuration
class Config {
	@Bean
	@ConditionalOnProperty(name = "intercept.methods", havingValue = "true")
	MyBean aspect() {
		return new MyBean();
	}
}
class MyBean {
	@EventListener(ApplicationStartedEvent.class)
	public void method() {
		System.out.println("life has many aspects");
	}
}




// TODOs
// a) inject a prop with @Value + default value
// b) inject it via @ConfigurationProperties record
// c) validate that property is at least 5 chars long. WHat happens if it's missing?
// d) define 2 beans (@Import style) and wire them via ctor
// e) change the property if the profile = "local"
// f) make 1 of the beans implement an interface and create a 2nd impl that is active only if profile = local
// g) change the property via command line args (-D....). try that on the jar in /target after mvn package