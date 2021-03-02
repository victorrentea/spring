package com.example.demo;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.jndi.JndiTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.naming.NamingException;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.sql.DataSource;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public DataSource dataSource(@Value("${jdbc.jndi}") String jndiName) throws NamingException {
		return (DataSource) new JndiTemplate().lookup(jndiName);
	}

}

@RefreshScope
@RestController
class Hello {
	@Value("${prop}")
	private String prop;

	@GetMapping( "hello")
	public String method()  {
		return "Prop : " + prop;
	}
}


@Entity
@Data
class Customer {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
}
@Data
class CustomerDto {
	private String name;
}