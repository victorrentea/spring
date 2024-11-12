package com.example.demo;

import com.zaxxer.hikari.HikariDataSource;
import io.micrometer.core.aop.TimedAspect;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

//@EnableCaching(order = 2)
@EnableConfigurationProperties(Config.class)
@SpringBootApplication
//@EnableScheduling
@EnableAsync
@RequiredArgsConstructor
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean // definesc programatic beanuri
	public TimedAspect timedAspect() {
    return new TimedAspect();
	}

	private final UserRepository userRepository;
	@EventListener(ApplicationStartedEvent.class)
	public void insertData(){
		for (int i = 0; i < 10; i++) {
			userRepository.save(new User()
					.setEmail("email"+i+"@db.com")
					.setName("name"+i));
		}
		System.out.println(userRepository.findAll());
	}
}
