package com.example.demo;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}


@Data
@Component
@ConfigurationProperties(prefix = "welcome")
class WelcomeConfig {
	private String welcomeMessage;
//	private int hostPort;
	private List<String> supportUrls;
	private Map<String, String> localContactPhone;
	private HelpInfo help;
	@Data
	static class HelpInfo {
		private URL helpUrl;
		private String iconUri;
	}
}


@RestController
@RequiredArgsConstructor
class PrimuRest {
	private final WelcomeConfig config;

	@Value("${welcome.welcomeMessage}")
	private String message;

	@GetMapping("hello")
	public String method() {
	    return config.toString();
	}

	// @ConfigurationProperties
	// de unde vin proprietatile
}


