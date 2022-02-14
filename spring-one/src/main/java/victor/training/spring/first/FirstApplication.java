package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// [1] Injection: field, constructor, method; debate; mockito
// [1] PostConstruct
// [2] Qualifier
// [3] Primary
// [4] Profile
// [5] getBean
// [6] inject List<BeanI>

@SpringBootApplication
@Configuration
public class FirstApplication implements CommandLineRunner{
	public static void main(String[] args) {
		SpringApplication.run(FirstApplication.class);
	}

	@Autowired
	private X x;



	@Override
	public void run(String... args) throws Exception {
		System.out.println(x.prod());
	}

//	@Bean
//	public X X() {
//		return new X();
//	}
}


//interface  X extends JpaRepository
@Component
@Retention(RetentionPolicy.RUNTIME)
@interface Facade {

}

@RequiredArgsConstructor
@Facade
//@Component // technical
//@Service // Domain Services : core logic
//@Repository  // for DB access, not used much since Spring Data
//@Controller // only if you are generating HTML on server-side : JSP/JSF/velocity/freemarker/thymeleaf
//@RestController // REST endpoints
class X {
	private final Y y;

	public int prod() {
		return 1 + y.prod();
	}
}

@Component
class Y {
	@Autowired
	private Z z;

	public int prod() {
		return 1 + z.prod();
	}
}
@Component
class Z {
	@Autowired
	private  Y y;


	public int prod() {
		return 1;
	}
}