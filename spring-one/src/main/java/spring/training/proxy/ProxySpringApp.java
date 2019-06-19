package spring.training.proxy;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@EnableCaching(order = 1)
@SpringBootApplication
public class ProxySpringApp implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(ProxySpringApp.class, args);
	}

	@Autowired
	private ExpensiveOps ops;

	// TODO [1] implement decorator
	// TODO [2] apply decorator via Spring
	// TODO [3] generic java.lang.reflect.Proxy
	// TODO [4] Spring aspect
	// TODO [5] Spring cache support
	// TODO [6] Back to singleton (are you still alive?)
	public void run(String... args) throws Exception {

		log.debug("Oare cine-i astA?" + ops.getClass());
		log.debug("\n");
		log.debug("---- CPU Intensive ~ memoization?");
		log.debug("10000169 is prime ? ");
		log.debug("Got: " + ops.isPrime(10000169) + "\n");
		log.debug("10000169 is prime ? ");
		log.debug("Got: " + ops.isPrime(10000169) + "\n");
		
		log.debug("---- I/O Intensive ~ \"There are only two things hard in programming...\"");
		log.debug("Folder MD5: ");
		log.debug("Got: " + ops.hashAllFiles(new File(".")) + "\n");
		log.debug("Got: " + ops.hashAllFiles(new File(".")) + "\n");


		log.debug("Detectez ca un fisier s-a modificat");
//		ops.aruncaIntrareaDinCache(new File("."));
		ops.aruncaToateIntrarile();

		log.debug("Folder MD5: ");
		log.debug("Got: " + ops.hashAllFiles(new File(".")) + "\n");
	}
	
}

@Retention(RetentionPolicy.RUNTIME)
@interface LoggedClass {}
@Retention(RetentionPolicy.RUNTIME)
@interface LoggedMethod {}

@Slf4j
@Aspect
@Component
class MethodLogger {
	@Order(10)
//	@Around("execution(* ExpensiveOps.*(..))")
//	@Around("execution(* *(..)) && @within(spring.training.proxy.LoggedClass)")
	@Around("execution(* *(..)) && @annotation(spring.training.proxy.LoggedMethod)")
	public Object interceptMethod(ProceedingJoinPoint point) throws Throwable {
		log.debug(">> Calling {}({})",
				point.getSignature().getName(),
				Arrays.toString(point.getArgs())
		);
		return point.proceed();
	}

}