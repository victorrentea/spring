package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@PropertySources({@PropertySource(value = "/secure/folder/app.properties", ignoreResourceNotFound = true)})
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

@RestController
class MyFirstRest {

	@Value("${prop}")
	private String prop;

	@GetMapping("/boot")
	public String hello() {
		return "Hello boot " + prop;
	}
}


