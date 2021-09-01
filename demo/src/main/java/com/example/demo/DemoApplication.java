package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

import java.net.URL;

@SpringBootApplication
//@ConditionaOn@
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Value("${some.prop}")
	private URL prop;
	@Value("${unchanged}")
	private String unchanged;

	@Autowired
	WelcomeInfo welcomeInfo;

	@EventListener(ApplicationStartedEvent.class)
	public void method() {
		System.out.println("WORKS! " + prop + " aaa " + unchanged);
		System.out.println("APPID: " + welcomeInfo.getHelp().getAppId());
	}

}

