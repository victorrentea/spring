package com.example.first;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication//(exclude = {JmxAutoConfiguration.class})
public class FirstApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstApplication.class, args);
	}

}


@Slf4j
@RequiredArgsConstructor
@RestController
class MyRestController {
	@Value("${welcome.welcomeMessage}")
	private String message;
	private final WelcomeInfo welcomeInfo;
	@GetMapping
	public String method() {
		log.debug("Some deep dark info about a bug you can't reproduce on your local");
	    return message;
	}
}