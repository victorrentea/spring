package victor.training.spring.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import victor.training.spring.di.different.Y;

// [1] Injection: field, constructor, method; debate; mockito
// [1] PostConstruct
// [2] Qualifier
// [3] Primary
// [4] Profile
// [5] getBean
// [6] inject List<BeanI>
// [7] @Value (+Lombok @RAC) + @ConfigurationProperties

//@ComponentScan(basePackages = {"victor.training.spring.different","victor.training.spring.di"})
@SpringBootApplication
public class FirstApplication implements CommandLineRunner{
	public static void main(String[] args) {
		SpringApplication.run(FirstApplication.class);
	}

	@Autowired // automatically injected despite the private visibility
	private X x;

	@Override
	public void run(String... args) throws Exception {
		System.out.println(x.prod());
	}
}

//@Bean in a @Configuration


//@Controller // in the ages of jsp (html on server) < avoid if exposing REST endpoints
//@RestController // REST apoi
@Service  // = business logic
//@Component // a kind of util/ technica infra stuff
//@Repository // DB access

//@Service
//@Retention(RetentionPolicy.RUNTIME)
//@interface Facade {}
//
//@Facade
class X {
	// #1 field injection by type
	@Autowired
	private Y y;

// #2 method (setter) injection (rarely used)
//	private Z z;
//	@Autowired
//	public void setZ(Z z) {
//		this.z = z;
//	}

	public int prod() {
		return 1 + y.prod();
	}
}

