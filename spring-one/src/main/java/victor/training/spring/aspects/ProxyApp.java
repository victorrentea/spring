package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

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
   // [4] Spring Cache support [opt: redis]
   // [5] Spring aspect, @Facade, @Logged
   // [6] Tips: self proxy, debugging, final
   // [7] OPT: Manual proxying using BeanPostProcessor

   // Holy Domain Logic.
   // Very precious things that I want to keep agnostic to technical details
   @Autowired
   private ExpensiveOps ops;// = new ExpensiveOps();

   public void run(String... args) {
      log.debug("Oare eu cu cine vorbesc ?" + ops.getClass());
      log.debug("\n");
      log.debug("---- CPU Intensive ~ memoization?");
      log.debug("10000169 is prime ? ");
      log.debug("Got: " + ops.isPrime(10000169) + "\n");
      log.debug("10000169 is prime ? ");
      log.debug("Got: " + ops.isPrime(10000169) + "\n");

      log.debug("---- I/O Intensive ~ \"There are only two things hard in programming: cache invalidation and naming things\"");
      log.debug("Folder . MD5: ");
      log.debug("Got: " + ops.hashAllFiles(new File(".")) + "\n");
      log.debug("Got: " + ops.hashAllFiles(new File(".")) + "\n");

      log.debug("Aflu ca s-a schimba un fisier (NIO)");
      ops.curataCachePtFolderul(new File("."));

      log.debug("Folder . MD5: ");
      log.debug("Got: " + ops.hashAllFiles(new File(".")) + "\n");
   }
}
@Retention(RetentionPolicy.RUNTIME)
@interface LoggedClass {}

@Slf4j
@Component
@Aspect
class MethodInterceptor {
//	@Around("execution(* victor.training..*.*(..))")
	@Around("execution(* *(..)) && @within(victor.training.spring.aspects.LoggedClass)")
   public Object intercept(ProceedingJoinPoint pjp) throws Throwable {
	   log.info("Calling method {}({})",
          pjp.getSignature().getName(),
          Arrays.toString(pjp.getArgs()));

      long t0 = System.currentTimeMillis();
      try {
         return pjp.proceed();
      } finally {
         long t1 = System.currentTimeMillis();
         log.debug("Took {} ms", t1 - t0);
      }
   }
}

