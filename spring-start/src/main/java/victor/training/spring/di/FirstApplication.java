package victor.training.spring.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.di.subp.Z;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// [1] Injection: field, constructor, method; debate; mockito
// [1] PostConstruct + EventListener + CommandLineRunner
// [2] Qualifier
// [3] Primary
// [4] Profile
// [5] getBean
// [6] inject List<BeanI>
@RestController
@Import(
		{
				X.class,
				Y.class,
				Z.class

				}
)
@SpringBootApplication(scanBasePackages = "notused")
public class FirstApplication /*implements CommandLineRunner*/ {
	public static void main(String[] args) {
		SpringApplication.run(FirstApplication.class);
	}

	@Autowired
	private X x;

	//	@Override
	//	public void run(String... args) throws Exception { // #1
	//	@PostConstruct // #2
//	@EventListener(ApplicationStartedEvent.class)

	@GetMapping
	public void method() {
		System.out.println("At startup ");
		System.out.println(x.prod());
	}
}

//@Service
record X(
		Y y
) {

	public int prod() {


		return 1 + y.prod();
	}
}

@Component
@Retention(RetentionPolicy.RUNTIME)
@interface Facade {

}

//@Service // /**/Business Logic (domain rules)
//@Repository // DB access <
//	//  import the repo into the configuration.
//@Controller // not used anymore - the old way of generating webpages: jsp, jsf, vaadin, gwt, ....velocity
//@RestController // REST APIs
//@Component // garbage (unclear, technical)
//
//@Configuration // contains @Bean definitions, not application code
@Facade
class Y {
	private final Z z;

	Y(Z z) {
		this.z = z;
	}
	//	private final ApplicationContext applicationContext;
//
//	public Y(ApplicationContext applicationContext) {
//		this.applicationContext = applicationContext;
//	}

	public int prod() {
//		Z z = applicationContext.getBean(Z.class); // avoid : gets to runtime tiem erorrs, not deploy-time = BAD.

		return 1 + z.prod();
	}
}
