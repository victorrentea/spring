package com.example.demo;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

//@PropertySource("c:/win")
@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableConfigurationProperties(Props.class)
//@ConfigurationPropertiesScan // a gasit-o @Teo
public class DemoApplication {

  public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	private UserRepository repo;
	@EventListener(ApplicationReadyEvent.class)
	public void init() {
		User user = new User();
		user.setName("Ion");
		repo.save(user);
//		HikariDataSource ds;
//		// java bean property: are si getter/setter
//		ds.setCatalog();
//		ds.getCatalog()
	}

}
