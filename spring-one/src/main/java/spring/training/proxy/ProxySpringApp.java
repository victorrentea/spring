package spring.training.proxy;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@EnableAspectJAutoProxy(exposeProxy = true)
@EnableCaching 
@SpringBootApplication
public class ProxySpringApp  {
	private final static Logger log = LoggerFactory.getLogger(ProxySpringApp.class);
	public static void main(String[] args) {
		SpringApplication.run(ProxySpringApp.class, args);
	}
	
	@Bean
	public BeanPostProcessor cacheAugmenter() {
		return new BeanPostProcessor() {
			public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//				if (bean.getClass().isAnnotationPresent(Facade.class)) {
//					log.debug("Wrapping in a proxy " + bean.getClass());
//					return ClassProxy.proxy(bean);
//				}
//				else {
					return bean;
//				}
			}
		};
	}
}
@Component
class Runner implements CommandLineRunner {
	private final static Logger log = LoggerFactory.getLogger(Runner.class);
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
	public void run(String... args) throws Exception {
		System.out.println("Cine esti? " + ops.getClass());
		log.debug("\n");
 		log.debug("---- CPU Intensive ~ memoization?");
		log.debug("10000169 is prime ? ");
		log.debug("Got: " + ops.isPrime(10_000_169) + "\n");
		log.debug("10000169 is prime ? ");
		log.debug("Got: " + ops.isPrime(10000169) + "\n");
		
		log.debug("---- I/O Intensive ~ \"There are only two things hard in programming...\"");
		log.debug("Folder . MD5: ");
		log.debug("Got: " + ops.hashAllFiles(new File(".")) + "\n");
		log.debug("Got: " + ops.hashAllFiles(new File(".")) + "\n");
		
		// Suppose I detect a change in a folder
		log.debug("I must throw away the cache for folder .");
		ops.aruncaCacheulPentruFolderul(new File("."));
		
		log.debug("Folder . MD5: ");
		log.debug("Got: " + ops.hashAllFiles(new File(".")) + "\n");
	}
}

@Retention(RetentionPolicy.RUNTIME)
@interface LoggedClass {
	
}
@Retention(RetentionPolicy.RUNTIME)
@interface LoggedMethod {
	
}

@Aspect
@Component
class MethodParamLogger {
	private final static Logger log = LoggerFactory.getLogger(MethodParamLogger.class);
//	@Around("execution(* spring..*.*(..))")
//	@Around("execution(* spring.training.proxy.ExpensiveOps.*(..))")
//	@Around("execution(* spring.training.proxy.*.*(..))")
//	@Around("execution(* *(..)) && @within(spring.training.proxy.LoggedClass)")
	@Around("execution(* *(..)) && @annotation(spring.training.proxy.LoggedMethod)")
	public Object xx(ProceedingJoinPoint point) throws Throwable {
		log.debug("Invoc metoda {} cu parametrii {}", 
				point.getSignature().getName(),
				Arrays.toString(point.getArgs()));
		return point.proceed();
	}
	
}
