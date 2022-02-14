package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@EnableAspectJAutoProxy(exposeProxy = true)
@EnableCaching (order = 10)
@SpringBootApplication
@Slf4j
public class ProxyApp implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(ProxyApp.class, args);
	}

	// [2] InterfaceProxy.proxy (no Spring)
	// [3] ClassProxy.proxy (no Spring)
	// [4] Spring Cache support [opt: redis]
	// [5] Spring aspect, @Facade, @Logged
	// [6] Tips: self proxy, debugging, final

	// Holy Domain Logic.
	// Very precious things that I want to keep agnostic to technical details
	@Autowired
	private ExpensiveOps ops;

	public void run(String... args) {
		log.debug("\n");
 		log.debug("---- CPU Intensive ~ memoization?");
		log.debug("10000169 is prime ? ");
		log.debug("Got: " + ops.isPrime(10000169) + "\n");
		log.debug("10000169 is prime ? ");
		log.debug("Got: " + ops.isPrime(10000169) + "\n");

		System.out.println(ops.secondMethod("param", 10000169));
	}
}

// I want to write a piece of code that logs all calls to methods in classes in this package victor.training.spring.aspects

@Slf4j
@Aspect
@Component
@Order(5)
class MethodLoggerInterceptor {

//	@Around("execution(* *DAO.*(..))") // NEVER
//	@Around("execution(* victor.training.spring..*.*(..))") // less
	@Around("@annotation(victor.training.spring.aspects.Logged) " + // on the method
			  "|| @within(victor.training.spring.aspects.Logged)") // on the class
	public Object method(ProceedingJoinPoint pjp) throws Throwable {
		log.info("Calling " + pjp.getSignature().getName() + " with args " + Arrays.toString(pjp.getArgs()));

		// WHAT NOT TO DO IN CUSTOM ASPECTS
		// network calls (eg DB, REST)
		// try NOT to do stuff that breaks flow (exceptions)
		Object result = pjp.proceed();
		return result;
	}
}
