package victor.training.spring.di;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

// [1] Injection: field, constructor, method; debate; mockito
// [1] PostConstruct

// [2] Qualifier
// [3] Primary
// [4] Profile
// [5] getBean
// [6] inject List<BeanI>

@EnableScheduling
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
	private final IY oups; // + lombok = ❤️


	@PostConstruct
	public void method() {
		System.out.println("why to do stuff at app startup: " + oups.prod());
		// example: some pre-calculatiuons, at startup, subscribe to topics, send a message "i'm alive", load a file,
	}

	public int prod() {
		return 1 + oups.prod();
	}
}

interface IY {
	int prod();
}

@Service
@RequiredArgsConstructor
class Y implements IY {
	private final Z z;

	public int prod() {
		System.out.println("DEFAULT IMPL");
		return 1 + z.prod();
	}
}

@Service
@RequiredArgsConstructor
@Primary
@Profile("fr")
class Gamma implements IY {

	public int prod() {
		System.out.println("Omlette au fromage 👏");
		return 12;
	}
}



@Service
@RequiredArgsConstructor
class Z {

	@Value("${john.name:defaultName}")
	private final String s;

	@Scheduled(cron = "${sched.cron}")
	public void cleanGarbage() {
		System.out.println("plastic garbage out on Wed");
	}
	@PostConstruct
	public void method() {
		System.out.println("POST: " +s);
	}

	public int prod() {
		return 1;
	}
}

@Profile("green")
@Component
class Grass {
	@PostConstruct
	public void method() {
		System.out.println("Walk, don't smoke");
	}
}

//@Profile("dummysecurityNOT_FOR_PRODUCTION") // okish
@Profile("!prod") // this means that in production you should start the app
	// with -Dspring.profiles.active=prod
	// DO NOT DO THIS!! dangerois. someone might forget
// http://localhost:8080?user=john#/path/angular
class DummySecurityConfig {}