package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SpringBootApplication
public class WithSpring {
   public static void main(String[] args) {
       SpringApplication.run(WithSpring.class, args);
   }
}


class A {
   private final B b;

   A(B b) {
      this.b = b;
   }

   public int toTest() {
      return b.risky() + 1;
   }
}
//class ServiceLocator {
//   private static final Map<Class<?>, ?> instances = new HashMap<>();
//
//   public  static <T> T getBean(Class<T> clazz) {
//      return (T) instances.get(clazz);
//   }
//
//   @VisibleForTesting
//   public <T> void setMockInstanceForTest(Class<T> toMock, T mock) {
////      instances.put(toMock, mock);
//   }
//}
@Retention(RetentionPolicy.RUNTIME)
@Component
@interface Facade {

}

@Facade // signal custom-made LAYERS.
//@Component // infra code
//@Service // business logic
//@Repository // DB acces <<
//@Controller // we don't use anymore. It was for JSP/velocity
//@RestController // @GetMapping ....
class B {
//   @Autowired // field injection
   private final CI c;
//   @Autowired
   private final D d;

//   EJB 2. // heavy, intrusive, xml, frustration, 5 classes for every logic class
//   Bean  ~lighter than Enterprise Java Bean @Autowired

   B(/*@Qualifier("classImpl2") */CI classImpl2, D d, ApplicationContext context) { // constructor injection
      this.c = classImpl2;
      this.d = d;
//      context.getBean("myarbName")

   }

//   @Autowired // method injection
//   public void setC(C c) {
//      this.c = c;
//   }

   @EventListener
   public void afterCreationOfB(ApplicationStartedEvent event) {
//   @PostConstruct
//      @Transactional
//   public void method()  {
      System.out.println("Hello events ! " + c);
      c.logic();
   }

   public int risky() {
      if (Math.random()<.5) {return 1;}
      throw new RuntimeException("Network call");
   }
}
interface CI {
   void logic();
}
//@Primary
@Component
class ClassImpl2 implements  CI {
   @Override
   public void logic() {
      System.out.println("C2");
   }
}
@Component
class C1 implements  CI {
   public void logic() {
      System.out.println("C1");
   }
}
@Component
class D {
   @Autowired
   private E e;
   @PostConstruct
   public void method() {
      System.out.println("D has e = " + e);
   }
}

@Component
class E {
   @Autowired
   private D d;// :) ha ha
   @PostConstruct
   public void method() {
      System.out.println("E has d = " + d);
   }

}

@Component
class MyFavourite implements CommandLineRunner {

   @Override
   @Transactional
   public void run(String... args) throws Exception {
      System.out.println("Startup");
   }
}