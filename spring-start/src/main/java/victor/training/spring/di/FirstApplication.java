package victor.training.spring.di;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
//@Component // a kind of util/ technica infra stuff
//@Repository // DB access

//@Service
//@Retention(RetentionPolicy.RUNTIME)
//@interface Facade {}
//
//@Facade

@RequiredArgsConstructor
@Service // & co. -> 1 instance only / application = "singleton"
@Slf4j
class X {
	private String currentUsername;
	// shared mutable state in a multi-threaded environment (REST API backend)
	// -> you're dead "race bug" -> impossible to reproduce

	public void setUsername(String user) {
		currentUsername = user;
	}

	public void doStuffForCurrentUser() {
		System.out.println("currentu ser = " + currentUsername);
	}


	// #1 field injection by type
	private final Y y;
	private final Y y2;
	private final Y y3;
	private final Y y4;
	private final Y y5;
	private final Y y6;
	private final Y y7;
	private final Y y8;

	// #2 method (setter) injection (rarely used)

//	private Z z;
//	@Autowired
//	public void anyMethod(Z z, Y y) {
//		this.z = z;
//		System.out.println(y);
//	}

	public int prod() {
//		log.debug();
		return 1 + y.prod();
	}


}

