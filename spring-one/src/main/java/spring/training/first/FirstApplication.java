package spring.training.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

// [1] Injection: field, constructor, method; debate; mockito
// [1] PostConstruct
// [2] Qualifier
// [3] Primary
// [4] Profile
// [5] getBean

@SpringBootApplication
public class FirstApplication implements CommandLineRunner{
	public static void main(String[] args) {
		SpringApplication.run(FirstApplication.class);
	}

	@Autowired
    private A a;

	@Override
	public void run(String... args) throws Exception {

	    a.m();

	}
}

//@Component
@Service
//@Repository
//@Controller
//@RestController
//@Configuration
//@MessageEndpoint
class A {
	private final B b;
	private final C c;

	@Autowired
	public A(B b, C c) {
		this.b = b;
		this.c = c;
	}
	public A(B b) {
		this.b = b;
		this.c = null;
	}
	public void m() {
    }
}

@Component
class B {
}
@Component
class C {
}