package victor.training.spring.springbasics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SpringBasicsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBasicsApplication.class, args);
	}
}

//@RequiredArgsConstructor
@Component // @Service @Repository @Controller @RestController @MessageEndpoint
class AutoRun implements CommandLineRunner {
	private final B b;
	private A a;
	private C c;

	AutoRun(B b) {
        System.out.println("NEW");
		this.b = b;
	}

	@Autowired
	public void oricum(CFactory cFactory, A a) {
        System.out.println("method injection");
		c = cFactory.createC();
		this.a = a;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hello " + a.m() + " -- " + c);
	}
}

@Service
class CFactory{
	public C createC() {return new C();}
}
class C {}


@Service
class B{
	public String m() {
		return "mb";
	}
}
@Service
class A {
	@Autowired
    private B b;

    public A() {
		System.out.println("In constructor: " + b);
	}

    @PostConstruct
	public void initializeXy() {
		System.out.println("POstConstruct poate invoca metode pe beanuri care ti-au fost date de sus, de Zana Spring " + b.m());
	}

    public String m() {
		return "Spring";
	}
}