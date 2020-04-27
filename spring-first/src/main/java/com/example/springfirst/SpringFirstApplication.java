package com.example.springfirst;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Slf4j
@SpringBootApplication
public class SpringFirstApplication implements CommandLineRunner {
//	private static final Logger log = LoggerFactory.getLogger(SpringFirstApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(SpringFirstApplication.class, args);
	}

	@Autowired
	private A a;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Start");
		a.m();
		log.debug("logu magic");
	}
}
@Facade
class A {
	@Autowired
	B b;
	public void m() {
	    b.met();
		EntitateHibernate e = new EntitateHibernate();
		e.setName("name");
		e.setPhone("8989989");
//	    new EntitateHibernate().setId(1l);
//		new EntitateHibernate().setStatus(EntitateHibernate.Status.DELETED);
	}
}

@Data
class QueryResult {
	private final long id;
	private final String name;
}

//@Data// niciodata pe entitati de dom
class EntitateHibernate{
	enum Status {
		ACTIVE,DRAFT,DELETED
	}
	@Getter
	private Long id;
	@Getter
	@Setter
	private String name;
	@Getter
	@Setter
	private String phone;
	@Getter
	private Status status;
	public void delete() {
		//verifica starea precedenta
		if (status != Status.ACTIVE) {
			throw new IllegalStateException();
		}
		status = Status.DELETED;
	}

}



@Service
@RequiredArgsConstructor
class B {
	private final C c;
	private final D d;
	private final D d2;
	private final D d3;
	private final D d4;
	private final D d5;
	private final D d6;



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
@Repository // general purpose
class D {
	public void met() {
		System.out.println("Hello Spring World");
	}
}

@Component
@Retention(RetentionPolicy.RUNTIME)
@interface Facade {
}
