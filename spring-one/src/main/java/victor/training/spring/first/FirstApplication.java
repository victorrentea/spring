package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.web.controller.util.TestDBConnection;

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
		new SpringApplicationBuilder(FirstApplication.class)
			.listeners(new TestDBConnection())
			.profiles("spa") // re-enables WEB nature (disabled in application.properties for the other apps not to start :8080)
			.run(args);
	}

	@Autowired
	private X x;

	@Override
	public void run(String... args) throws Exception {
//		System.out.println(x.prod());
	}
}
@RestController// ==> "xLikeInEnterprise" bean name
class X {
	// field injection
//	@Autowired
//	private Y y;

	private Z z;
	// method (setter) injection
	@Autowired
	public void setZ(Z z) {
		this.z = z;
	}
	@Autowired
	ApplicationContext applicationContext;
	@GetMapping("di")
	public int prod() {
		System.out.println("App worked until now");

		 // 3 reasons NEVER to do this:
		// 1) it allows cyclic deps
		// 2) runtime failure if missing bean
		// 3) +1 lin eof shitty code.
		Y yProgramatically = applicationContext.getBean(Y.class); // programmatic retrival of deps.
		return 1 + yProgramatically.prod();
	}
}
@Service
class Y {
	private final Z z;
	// constructor injection (no @Autowired needed since Spring 4.3)
	public Y(Z z, @Value("${welcome.welcome-message}") String m) {
		this.z = z;
		//everything you need is here.
		// @PostConstruct is an anti-pattern if using constructor-based injection as we USUALLY do.
	}
//	@Autowired
//	private Z z;

	public int prod() {
		return 1 + z.prod();
	}
}
@Facade
//@Component // the rest. mappers, infra code...
//@Service // DOMAIN services
//@Repository // repo layer (DB) - not used anymore much> because Spring Data
//@Controller // <<<<< used only in apps that generate HTML on server-side like thymeleaf
//@RestController // anything returned is marshalled as JSON (by default) to the HTTP client
//@MessageListener
class Z {
	@Autowired
	private ApplicationContext applicationContext;
	public Z() {

		System.out.println("ONCE! Life of a Singleton");
	}

	public int prod() {
		Y yProgramatically = applicationContext.getBean(Y.class); // programmatic retrival of deps.
		System.out.println("y retrieved lazily " + yProgramatically);
		return 1;
	}
}


//interface TrainingRepo extends JpaRepository<Training, Long> {}
@Component
@Retention(RetentionPolicy.RUNTIME)
@interface Facade {
}