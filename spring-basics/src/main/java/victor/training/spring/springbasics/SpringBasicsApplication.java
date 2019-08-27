package victor.training.spring.springbasics;

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
class B{}
@Service
class CFactory{
	public C createC() {return new C();}
}
class C {}

@Service
class A {

    private B b;

    @Autowired
    public void setB(B b) {
        System.out.println("Ii dau lui A pe un B");
        this.b = b;
    }

    public String m() {
		return "Spring";
	}
}