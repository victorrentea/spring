package victor.training.spring.di;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
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

@SpringBootApplication
public class FirstApplication {
	public static void main(String[] args) {
		SpringApplication.run(FirstApplication.class);
	}

	@Autowired
	private X x;

	@EventListener(ApplicationStartedEvent.class)
//	@EventListener(ApplicationStartingEvent.class)
//	@EventListener(ApplicationReadyEvent.class)
	public void run() throws Exception {
		System.out.println(x.prod());
	}
}

@Component
@interface ApplicationService {

}

//@Service // = business logic (the stuff in Confluence) Br-73
//@ApplicationService // =
//
//@Repository // access to DB but not necessary if you extend from JpaRepository
//@Configuration //  [containing @Bean] not for containing biz logic, but for defining @Bean
//@Controller // used in the good'ol days of JSP/velocity/freemarker/jsf = server-side HTML rendering.
//@RestController // REST apis + SPA in JS in browser


@Slf4j
@RequiredArgsConstructor // invisible constructor
@Component // = garbage not biz logic.
class X { // spring creates automatically 1 instance "singleton" life cycle.

	// field
//	@Autowired
//	private Y y;
//
//	@Autowired
//	public void setY(Y y) { ?/ don't use tyhis!
//		this.y = y;
//	}

	// ctor injection is better when you create an instance without spring.
	private final Y y; // + lombok = ❤️


	@PostConstruct
	public void method() {
		System.out.println("why to do stuff at app startup: " + y.prod());
		// example: some pre-calculatiuons, at startup, subscribe to topics, send a message "i'm alive", load a file,
	}

	public int prod() {
		return 1 + y.prod();
	}
}



@Service
class Y {
	private final Z z;


	// constructor injection (no @Autowired needed since Spring 4.3)
	public Y(Z z) {
		this.z = z;
	}

	public int prod() {
		return 1 + z.prod();
	}
}
@Service
@RequiredArgsConstructor
class Z {

	@Value("${john.name}")
	private final String s;

	@PostConstruct
	public void method() {
		System.out.println("POST: " +s);
	}

	public int prod() {
		return 1;
	}
}