package victor.training.spring.springbasics;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class SpringBasicsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBasicsApplication.class, args);
	}
}

@RequiredArgsConstructor
@Component // @Service @Repository @Controller @RestController @MessageEndpoint
class AutoRun implements CommandLineRunner {
	private final A a;
	private final B b;
	private C c;

	@Autowired
	public void setC(CFactory cFactory) {
		c = cFactory.createC();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hello " + a.m() + " -- " + c);

	}
}
@Service
class B{}
@Service
class CFactory{
	public C createC() {return new C();}
}
class C {}

@Service
class A {
	public String m() {
		return "Spring";
	}
}