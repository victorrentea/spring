package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SpringBootApplication
public class DemoApplication {

//	@Value("${greeting:Default Value = BAD PRACTICE caci nu sunt toate props evidente din application.properties}")
	@Value("${greeting}")
	private List<Integer> greeting;

	@Autowired
	private WelcomeInfo welcomeInfo;
	//in afara de String mai poti inejcta:
	// int, long, LocalDate, File, Resource~File mai smecher, List , Duration
	// Map<k,v>, List<Structuri>

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("hello")
	public String hello() {
		return "Hello "  + welcomeInfo;
	}

}


//