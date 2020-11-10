package victor.training.spring.aspects;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
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
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

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
		//Ce face Spring pe sub
//		ExpensiveOps altaInstanta = ops;


//
//		ops = new ExpensiveOps() {
//			@Override
//			public Boolean isPrime(int n) {
//				// ma uit in cache. Daca e ==> return
//				// else
////				return super.isPrime(n); // nu cheama super.isPrime ci:
//				return altaInstanta.isPrime(n);
//			}
//		};
		log.debug("Oare cine e ops?:" + ops.getClass());

		log.debug("\n");
 		log.debug("---- CPU Intensive ~ memoization?");
		log.debug("10000169 is prime ? ");
		log.debug("Got: " + ops.isPrime(10000169) + "\n");
		log.debug("10000169 is prime ? ");
		log.debug("Got: " + ops.isPrime(10000169) + "\n");


		// Enhancer
//		Callback interceptor = new MethodInterceptor() {
//			@Override
//			public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
//				// ma ui in cache. daca gasesc valoarea o intorc. daca nu, apelez mai departe metoda reala
//				return null;
//			}
//		};
//		ExpensiveOps proxy = (ExpensiveOps) Enhancer.create(ExpensiveOps.class, interceptor);


		log.debug("---- I/O Intensive ~ \"There are only two things hard in programming...\"");
		log.debug("Folder . MD5: ");
		log.debug("Got: " + ops.hashAllFiles(new File(".")) + "\n");
		log.debug("Got: " + ops.hashAllFiles(new File(".")) + "\n");
		log.debug("Folder . MD5: ");
		log.debug("Got: " + ops.hashAllFiles(new File(".")) + "\n");
	}
}
@Retention(RetentionPolicy.RUNTIME)
@interface LoggedClass {
}

@Component
@Aspect
@Slf4j
class LoggingAspect {
//	@Around("execution(* victor..*DAO.*(..))") //class name conventions: urat
//	@Around("execution(* victor..*.*(..))") // package scoped
	@Around("execution(* *(..)) && @within(victor.training.spring.aspects.LoggedClass)") // class level annotations
	public Object logCall(ProceedingJoinPoint pjp) throws Throwable {
		// ce faci tu cu asteaa: logging, masori timpul, pre-check mai custom
		log.debug("Calling method {} cu arg {}",pjp.getSignature().getName(), Arrays.toString(pjp.getArgs()));
		return pjp.proceed();
	}
}

