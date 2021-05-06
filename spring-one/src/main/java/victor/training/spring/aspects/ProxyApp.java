package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableCaching
@SpringBootApplication
@Slf4j
@EnableAspectJAutoProxy(exposeProxy = true)
public class ProxyApp implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ProxyApp.class, args).close();
	}

	// > Interface Proxy with JDK
	// > Class Proxy with CGLIB
	// > @Cacheable + redis
	// > LoggerInterceptor @Aspect, @Facade, @Logged
	// > Tips: self proxy, debugging, final
	// > [OPT] AspectJ Load-Time-Weaving: aop.xml, agent, @EnableCaching(ASPECTJ)

	@Autowired
	private ExpensiveOps ops;// = new ExpensiveOps();
	@Autowired
	private BreakItForTheFramework anotherClass;

	// Holy Domain Logic: Precious things that I want to keep agnostic to technical details
	public void run(String... args) {
		log.debug("Using class: " + ops.getClass());

 		log.debug("---- CPU Only Work = memoization");
		log.debug("10000169 is prime ? ");
		log.debug("Got: " + ops.isPrime(10000169) + "\n");
		log.debug("10000169 is prime ? ");
		log.debug("Got: " + ops.isPrime(10000169) + "\n");
		anotherClass.anotherMethod();


//		log.debug("---- Local copy of remote data ==> Possible inconsistencies");
//		log.debug("Users: " + ops.getAllUsers());
//		log.debug("Users: " + ops.getAllUsers());
//		ops.createUser(new User());
//		log.debug("Users: " + ops.getAllUsers());
	}
}

