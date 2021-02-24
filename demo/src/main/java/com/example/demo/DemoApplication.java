package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

@RestController
class PrimuRest {

	@Value("${welcome.welcomeMessage}")
	private String message;

//	@Value("${welcome.localContactPhone}")
//	Map<String, String> phones;

	@GetMapping("hello")
	public String method() {
	    return message;
	}

	// @ConfigurationProperties
	// de unde vin proprietatile
}
