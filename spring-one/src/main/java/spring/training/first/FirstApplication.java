package spring.training.first;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class FirstApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstApplication.class, args);
	}
}

@Service
@Retention(RetentionPolicy.RUNTIME)
@interface Facade {}


@RequiredArgsConstructor
@Facade
class A {
	private final B b;

	@Autowired
//	public void myInit(B b, @Nou IC c) {
	public void myInit(B b, IC c) {

		System.out.println("Method injection");
	}

	@PostConstruct
	public void gatafrate() {
        System.out.println("Hello frate " + b);
	}

	public String m() {
		return b.n().toUpperCase();
	}
}

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@interface Nou {}


// daca asta ar fi intr-un JAR care doar uneori e pun in classpath si atuncicand e, trebuie sa castige
@Primary
@Component
@Profile("localDev")
class CVechi implements IC {
	private int totalOrderPrice;

	@Override
	public void compute(Order order) {
		totalOrderPrice += order.getPrice();
	}
	@PostConstruct
	public void hello() {

		// BUG aici
		System.out.println("Legacy/ Mereu e acolo. dar nimeni nu vorbeste de el.");
	}
}
@Nou
@Component
class CNou implements IC {
	private int totalOrderPrice;

	@Override
	public void compute(Order order) {
		totalOrderPrice += order.getPrice();
	}
}

class Order {

	public int getPrice() {
		return 1;
	}
}


