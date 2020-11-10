package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.io.File;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private DataSource dataSource;

	@Value("${my.prop}")
	private Integer myProp;
	@Value("${alta.prop}")
	private Integer alta;
	@Autowired
	private Authenticator authenticator;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	public void run(String... args) throws Exception {
		System.out.println("Folosesc " + dataSource);
		System.out.println("My propsX: " + myProp);
		System.out.println(authenticator);
	}
}

@RestController
 class Hello {
	@GetMapping
	String hello( 	) {
		return "xyz";
	}
}

interface Authenticator {

}
@Profile("prod")
@Component
class SSOAuthenticator implements  Authenticator{
//	@Value("${in.folder}")
//	private File inFolder;
}
@Profile("!prod")
@Component
class DummyAuthenticator implements  Authenticator{

}