package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

	@Value("${prop}")
	private String prop;
	@Autowired
	SomeContract contract;

	// GET   /test
//	@RequestMapping(value = "test", method = RequestMethod.GET)
	@GetMapping("test")
	public String test() {
		return contract.convert(prop);
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}

interface SomeContract {
	String convert(String property); // a way of doing some computation.
}
@Profile("austria")
@Component
class ToUpperCase implements SomeContract{
	@Override
	public String convert(String property) {
		return property.toUpperCase();
	}
}
@Profile("italy")
@Component
class ToLowerCase implements SomeContract{
	@Override
	public String convert(String property) {
		return property.toLowerCase();
	}
}

//@Configuration
//@Profile("bypassSecurity")
//class C {
//}