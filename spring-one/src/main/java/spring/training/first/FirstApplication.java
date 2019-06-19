package spring.training.first;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import javax.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.LifecycleProcessor;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import spring.training.B;

@SpringBootApplication
public class FirstApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstApplication.class, args);
	}
}

@Service
@Retention(RetentionPolicy.RUNTIME)
@interface Facade {

}


@RequiredArgsConstructor
@Facade
class A {
	private final B b;

	@Autowired
	public void myInit(B b, C c) {
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


@Component
class C {}



