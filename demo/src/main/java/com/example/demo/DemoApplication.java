package com.example.demo;

import com.example.demo.controller.WelcomeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

//@EnableCaching -- re-enable to test redis :)
@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableConfigurationProperties(WelcomeInfo.class)
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}





@Component
class A implements CommandLineRunner {
	private final B b;

	public A(B b) {
		this.b = b;
	}

	public void m() {
	    b.m();
	}

	@Override
	public void run(String... args) throws Exception {
		m();
	}
}
@Component
class B {
	@Autowired
	private C c;
	private D d;

	@Autowired
	public void setD(D d) {
		this.d = d;
	}

	public void m() {
	    c.m();
		System.out.println("D : " + d);
	}
}
@Component
class C {
	public void m() {
		System.out.println("Hello Spring DI");
	}
}
@Component
class D{

}



// ======================


//
//// de ce nu statice tot? 1 : netestabil 2: lifecyle: pre devreme de creat chestii in context static
//class A {
//	private B b;
//	public A(B b) {
//
////		b = new B(c, ds);
//		this.b = b;
//	}
//	public void ma() {
//	    b.mb();
//	}
//}
//
//class B {
//	// PULLing my deps
//	private C c;
//	private DataSource ds;
//	public B(C c, DataSource ds) {
//		this.c = c;
////		ds = ServiceLocator.getDataSource();
////		c = ServiceLocator.getC();
//		this.ds = ds;
//	}
//
//	public void mb() {
//		try {
//			ds.getConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//}
//
//class C{
//}