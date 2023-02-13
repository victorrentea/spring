package victor.training.spring.di;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
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

//@Service // business logic

//@Repository  // for DB queries, but fun fact Spring Data Repo interfaces don't need it

//@Controller // no longer used if exposing REST api. (used for jsp/jsf/vaadin -> server-side rendered HTML)
//@RestController // REST API
// NOT @Entity <- hibernate instantiates those.

//@Mapper
@Slf4j // <-- adds this private static final Logger log = LoggerFactory.getLogger(X.class);
@RequiredArgsConstructor // <- defines contructor in .class file with all the final fields
@Service // use for ....? idk garbage TBD  Util .. CustomerMapper
class X {
//	@Autowired
//	private Y y; // field based injection

	// constructor-injection is recommended. bescause of tests.
	// but in practice we unit test classes with @InjectMocks who works with a) @Autowire b) constructor
	private final Y y;

	private Y y2;
	@Autowired
	public X anything(Y y2) {
		this.y2 = y2;
		return this;
	}

	public int prod() {
		return 1 + y.prod();
	}
}
@Service
class Y implements CommandLineRunner{
	private final Z z;

	// #3 constructor injection (no @Autowired needed since Spring 4.3)
	public Y(Z z) {
		this.z = z;
	}


//	@Autowired // abit of abuse ?!
//	@PostConstruct // recommended, but is pretty coarse-grained about WHEN?
	@EventListener(ApplicationStartedEvent.class) // just one of the 12 lifecycle events thrown by Spring at startup
	public void helloSwift() {
		System.out.println("Moving swiftly. WHY?! What to do in here?");
		System.out.println("Eg.v warm up a cache, load some more config, ping redis, send a message, validate a folder exists");
	}
	@Override // spring sees your bean implementing the CLR interface => calls this method with the program argumens
	public void run(String... args) throws Exception {
		System.out.println("Mee too");
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