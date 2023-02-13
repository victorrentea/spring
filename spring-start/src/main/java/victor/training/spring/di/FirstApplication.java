package victor.training.spring.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
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
// [7] @Value (+Lombok @RAC) + @ConfigurationProperties

@SpringBootApplication
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
}

@Component
@Retention(RetentionPolicy.RUNTIME)
@interface Mapper {}
//  registers the class in the spring context (aka bucket of instances)
	// Spring will create 1 single instance of this aka Singleton pattern
//@Component // use for ....? idk garbage TBD  Util .. CustomerMapper

//@Service // business logic

//@Repository  // for DB queries, but fun fact Spring Data Repo interfaces don't need it

//@Controller // no longer used if exposing REST api. (used for jsp/jsf/vaadin -> server-side rendered HTML)
//@RestController // REST API
// NOT @Entity <- hibernate instantiates those.

@Mapper
class X {
	@Autowired
	private Y y;

	public int prod() {
		return 1 + y.prod();
	}
}
@Service
class Y {
	private final Z z;

	// #3 constructor injection (no @Autowired needed since Spring 4.3)
	public Y(Z z) {
		this.z = z;
	}

	public int prod() {
		return 1 + z.prod();
	}
}
@Service
class Z {

	public int prod() {
		return 1;
	}
}