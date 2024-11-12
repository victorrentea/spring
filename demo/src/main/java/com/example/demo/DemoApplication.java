package com.example.demo;

import com.zaxxer.hikari.HikariDataSource;
import io.micrometer.core.aop.TimedAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

//@EnableCaching(order = 2)
@EnableConfigurationProperties(Config.class)
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean // definesc programatic beanuri
	@Order(1)
	public TimedAspect timedAspect() {
    return new TimedAspect();
	}
//	@Bean
//	public DataSource dataSource() {
//		HikariDataSource hikariDataSource = new HikariDataSource();
//		hikariDataSource.setConnectionTimeout();
//		return hikariDataSource;
//	}

	@Autowired
	public void method(ApplicationContext springContext) {
//		String property = springContext.getEnvironment().getProperty("spring.datasource.type");

	}
}
