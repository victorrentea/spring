package victor.training.spring.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
	private final Y y;
	private final Y y2;
	private final Y y3;
	private final Y y4;
	private final Y y5;
	private final Y y6;
	private final Y y7;
	private final Y y8;

	// #3 ctor based injection = better because you can instantiate (in tests) the class w/o the framework
	public X(Y y, Y y2, Y y3, Y y4, Y y5, Y y6, Y y7, Y y8) {
		this.y = y;
		this.y2 = y2;
		this.y3 = y3;
		this.y4 = y4;
		this.y5 = y5;
		this.y6 = y6;
		this.y7 = y7;
		this.y8 = y8;
	}

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

