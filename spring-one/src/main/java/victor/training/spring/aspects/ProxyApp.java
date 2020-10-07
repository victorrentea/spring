package victor.training.spring.aspects;

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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

@EnableAspectJAutoProxy(exposeProxy = true)
@EnableCaching 
@SpringBootApplication
@Slf4j
// [RO] "Viata e complexa si are multe aspecte" - Cel mai iubit dintre pamanteni. Spring e viata. :)
public class ProxyApp implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(ProxyApp.class, args);
	}

	// [1] Implement decorator with Spring
	// [2] InterfaceProxy.proxy (no Spring)
	// [3] ClassProxy.proxy (no Spring)
	// [4] Spring Cache support [opt: redis]
	// [5] Spring aspect, @Facade, @Logged
	// [6] Tips: self proxy, debugging, final
	// [7] OPT: Manual proxying using BeanPostProcessor

	// Holy Domain Logic.
	// Very precious things that I want to keep agnostic to technical details
	@Autowired
	private ExpensiveOps ops;



	public void run(String... args) {
		log.debug("Pai io daca chem o functie, d-apai eu chem functia, nu ?");
		log.debug("NU");
		log.debug("pentru ca apelul tau ajunge la " + ops.getClass());
		log.debug("\n");
 		log.debug("---- CPU Intensive ~ memoization?");
		log.debug("10000169 is prime ? ");
		log.debug("Got: " + ops.isPrime(10_000_169) + "\n");
		log.debug("10000169 is prime ? ");
		log.debug("Got: " + ops.isPrime(10_000_169) + "\n");

		ops.altaMetoda();
		
//		log.debug("---- I/O Intensive ~ \"There are only two things hard in programming...\"");
//		log.debug("Folder . MD5: ");
//		log.debug("Got: " + ops.hashAllFiles(new File(".")) + "\n");
//		log.debug("Got: " + ops.hashAllFiles(new File(".")) + "\n");
//		log.debug("Folder . MD5: ");
//		log.debug("Got: " + ops.hashAllFiles(new File(".")) + "\n");
	}
}

@Retention(RetentionPolicy.RUNTIME)
@interface LoggedClass {

}

@Slf4j
@Aspect
@Component
class LoggingAspect {

//	@Around("execution(* victor..*.*(..))")
	@Around("@within(victor.training.spring.aspects.LoggedClass)")
	public Object logParametersAndExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
		long t0 = System.currentTimeMillis();

		log.info("Calling {}({})", pjp.getSignature().getName(), Arrays.toString(pjp.getArgs()));
//		codu de rulat

		Object result = pjp.proceed();

		long t1 = System.currentTimeMillis();

		log.info("Took {} ms", t1-t0);

		return result;
	}
}