package victor.training.springdemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class SpringDemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hello Spring!");

		new A(B.getInstance()).f();
	}
}

class A {
	private final B b;
	A(B b) {
		this.b = b;
	}
	public void f() {
		b.g();
	}
}
//@Service
class B {
	private static B INSTANCE;
	private final C c;
	public static B getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new B();
		}
		return INSTANCE;
	}

	private B() {
		this.c = new C();
	}

	public void g() {
		System.out.println("Aici in beci");
	}
}
class C {}
