package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;

//@PropertySource("c:/win")
@SpringBootApplication
@EnableConfigurationProperties(Props.class)
//@ConfigurationPropertiesScan // a gasit-o @Teo
public class DemoApplication {

  public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
