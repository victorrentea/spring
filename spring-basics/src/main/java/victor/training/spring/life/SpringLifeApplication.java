package victor.training.spring.life;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SpringLifeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringLifeApplication.class, args);
	}

	@Autowired
	private B b;

	@Override
	public void run(String... args) throws Exception {

	}
}

@Component
class A {
	private final B b;

	A(B b) {
		this.b = b;
	}
}
@Component
@Scope("prototype")
class B {
	@PostConstruct
	public void unuNou() {
		System.out.println("un nou B");
	}
}