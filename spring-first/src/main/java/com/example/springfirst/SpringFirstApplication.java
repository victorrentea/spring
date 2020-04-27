package com.example.springfirst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SpringBootApplication
public class SpringFirstApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(SpringFirstApplication.class, args);
	}

	@Autowired
	private A a;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Start");
		a.m();

	}
}
@Facade
class A {
	@Autowired
	B b;
	public void m() {
	    b.met();
	}
}
@Service
class B {
	@Autowired
	C c;
	public void met() {
		c.met();
	}
}

@Repository // general purpose
class C {
	public void met() {
		System.out.println("Hello Spring World");
	}
}

@Component
@Retention(RetentionPolicy.RUNTIME)
@interface Facade {
}
