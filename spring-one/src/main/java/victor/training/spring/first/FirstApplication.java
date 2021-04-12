package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

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
public class FirstApplication implements CommandLineRunner{
	public static void main(String[] args) {
		SpringApplication.run(FirstApplication.class);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}

@Retention(RetentionPolicy.RUNTIME)
@Component
@interface Facade {

}

//@Component // technical (former UTILs classes), infra
//@Service // biz rules, logic
//@Controller // JSP/velocivty server-side generated HTML
//@RestController
//@Repository // access DB not really used much anymore with Spring Data Jpa
//@Configuration : for defininig Beans
@Facade
class A {

	public String gimme() {
		throw new IllegalArgumentException();
	}
}

@RequiredArgsConstructor
@Service
class B {
	private final A a;

	@Value("${s.prop}")
	private String s;

	public String method() {
	    String s = a.gimme();
	    return s.toUpperCase();
	}
}



//class B2 {
//	@Resource
////	@Autowired
//	private A a;
//}
