package spring.training.proxy;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@EnableAspectJAutoProxy(exposeProxy = true)
@EnableCaching
@SpringBootApplication
@Slf4j
public class ProxyApp implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(ProxyApp.class, args);
	}

	// [1] Implement decorator with Spring
	// [2] InterfaceProxy.proxy (no Spring)
	// [3] ClassProxy.proxy (no Spring)
	// [4] Spring Cache support
	// [5] Spring aspect, @Facade, @Logged1 
	// [6] Tips: self proxy, debugging, final
	// [7] OPT: Manual proxying using BeanPostProcessor23

	// Holy Domain Logic.
	// Very precious things that I want to keep agnostic to technical details
	@Autowired
	private ExpensiveOps ops;
	@Autowired
	ClasaAltuia alta;

	public void run(String... args) {
		alta.m();
		log.debug("\n");
		log.debug("ops.class = " + ops.getClass());
 		log.debug("---- CPU Intensive ~ memoization?");
		log.debug("10000169 is prime ? ");
		log.debug("Got: " + ops.isPrime(10_000_169) + "\n");
		log.debug("10000169 is prime ? ");
		log.debug("Got: " + ops.isPrime(43) + "\n");
		log.debug("Got: " + ops.isPrime(42) + "\n");
		log.debug("Dupa asta, tre un log");
		log.debug("Got: " + ops.isPrime(10000169) + "\n");

		log.debug("---- I/O Intensive ~ \"There are only two things hard in programming...\"");
		log.debug("Folder . MD5: ");
		log.debug("Got: " + ops.hashAllFiles(new File(".")) + "\n");
		log.debug("Got: " + ops.hashAllFiles(new File(".")) + "\n");
		log.debug("Daca se schimba aici un fis");
		ops.evictFolderCache(new File("."));

		log.debug("Folder . MD5: ");
		log.debug("Got: " + ops.hashAllFiles(new File(".")) + "\n");
	}
}

@Service
class ClasaAltuia {
	public void m() {
		System.out.println("Ceva");
	}
}
@Retention(RetentionPolicy.RUNTIME)
@interface LoggedMethod {}
@Retention(RetentionPolicy.RUNTIME)
@interface LoggedClass {}

@Component
@Aspect
@Slf4j
class MyLoggingAspect {
	//private static final Logger log = LoggerFactory.getLogger(MyLoggingAspect.class);
//	@Around("execution(* spring.training.proxy.*.*(..))")
//	@Around("execution(* spring..*.*(..))") // orice subpachet
//	@Around("execution(* *(..)) && @annotation(spring.training.proxy.LoggedMethod)")
	@Around("execution(* *(..)) && @within(spring.training.proxy.LoggedClass)")
	public Object logAround(ProceedingJoinPoint point) throws Throwable {
		log.debug("AOP: Calling method {} with param {}",
				point.getSignature().getName(),
				Arrays.toString(point.getArgs()));
		return point.proceed(); //lasa apelul sa ajunga la metoda reala
	}
}
