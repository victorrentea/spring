package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}

class ServiceLocator {
	private static Repo repo;

	public static Repo getRepo() {
		if (repo == null) {
			repo = new Repo(new X(new XA()), new Y(), DBManager.getDataSource());
		}
		return repo;
	}
	public static B2 getB2() {
		return null;
	}
}



class DBManager {
	static DataSource getDataSource() {
 return null;
	}
}

class A {
	public static void m() {
		ServiceLocator.getRepo().getUserById(1L);
	}
}




class B {

	public static void m() {
		ServiceLocator.getRepo().getUserById(1L);
	}

}
class B2 {
	public static void m2() {
		ServiceLocator.getRepo().getUserById(1L);
	}
}

interface IRepo {
	void getUserById(long l);
} // pentru teste, decorator, proxy te imping spre OOP -> met nestatice

class DummyRepo implements IRepo {
//	Map<>
	public void getUserById(long l) {
	}
}
//@Service
class X {
//	@Autowired
	private XA xa;
}

//@Service
class XA {

}
class Y{}
class Repo implements  IRepo {
	private final X x ;
	private final Y y ;
	private final DataSource dataSource; // depend necesare tuturor metodelor unei clasa-> prin constructor

	public Repo(X x, Y y, DataSource dataSource) {
		this.x = x;
		this.y = y;
		this.dataSource = dataSource;
	}

	public void getUserById(long l) {
//		dataSource.getConnection();
	}
}