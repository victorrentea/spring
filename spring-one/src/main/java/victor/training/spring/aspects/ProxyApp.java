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
import org.springframework.stereotype.Component;

import java.util.Arrays;

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
//		ExpensiveOps ops = new ExpensiveOps();
		log.debug("\n");
		log.debug("ops injectat de spring este : " + ops.getClass());
 		log.debug("---- CPU Intensive ~ memoization?");
		log.debug("10000169 is prime ? ");
		log.debug("Got1: " + ops.isPrime(10000169) + "\n");
		log.debug("10000169 is prime ? ");
		log.debug("Got2: " + ops.isPrime(10000169) + "\n");
		// cacheul e plin deja (stie de 100000169)
		ops.oareMergeCacheableDacaInvocareaELocala_NU();
	}
}

@interface Logged {

}

@Slf4j
@Aspect
@Component
class LoggingInterceptor {
	@Around("execution(* victor..*.*(..) )")
//	@Around("@annotation()")
	public Object interceptAndLog(ProceedingJoinPoint point) throws Throwable {
		log.info("Calling method {} with args {}",
			point.getSignature().getName(),
			Arrays.toString(point.getArgs()));
		long t0 = System.currentTimeMillis();

		Object ret = point.proceed();

		long t1 = System.currentTimeMillis();
		log.info("Returned value: " +ret);
		return ret;
	}
}