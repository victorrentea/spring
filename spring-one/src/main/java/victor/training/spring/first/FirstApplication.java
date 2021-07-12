package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

// [1] Injection: field, constructor, method; debate; mockito
// [1] PostConstruct
// [2] Qualifier
// [3] Primary
// [4] Profile
// [5] getBean
// [6] inject List<BeanI>

@SpringBootApplication
public class FirstApplication implements CommandLineRunner{
	public static void main(String[] args) {
		SpringApplication.run(FirstApplication.class);
	}

	@Autowired
	private ApplicationContext spring;

	@Override
	public void run(String... args) throws Exception {
		System.out.println(spring.getBean(A.class).method());
		System.out.println(spring.getBean(A.class).method());
		System.out.println(spring.getBean(A.class).method());
		System.out.println(spring.getBean(A.class).method());
		System.out.println(spring.getBean(A.class).method());
		System.out.println(spring.getBean(A.class).method());
	}
}

@Component // oaia neagra: cand nu stii ce sa-i pui, pui asta.
//@Service // Business Logic
//@Controller // cand implementezi pe server generarea de HTML: JPS, JSF, velocity,
//@RestController // GET, PUT, POST,DELETE
//@Repository // acces la DB
class A { // cate
	private final B b;

	private A(B b) {
		System.out.println("UNA");
		this.b = b;
	}

	public int method() {
		return b.methodB(1) * 2;
	}
}

@Component
class B {
	public int methodB(int x) {
		// munca multa testata separat
		return x + 1;
	}
}

