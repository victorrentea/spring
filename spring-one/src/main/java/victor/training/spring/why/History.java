package victor.training.spring.why;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class History implements CommandLineRunner {
   public static void main(String[] args) {
       SpringApplication.run(History.class, args);
   }

   @Autowired A a;

   @Override
   public void run(String... args) throws Exception {
      a.method();
   }
}

//class ServiceLocator {
//   Map<Class<?>, Object> singletons = new HashMap<>();
//   public static <T>  T getInstance(Class<T> bClass) {
//   }
//
//   public static <T> void setMockFromTests(Class<T> clazz, T mock) {
//
//   }
//}

@Service
@RequiredArgsConstructor
class A {
   private final B b;

   public void method() {
      b.method();
   }

   public void me() {
      b.method();
   }
}
//@Component
//@interface Facade {
//}

@Service // domain logic
//@Component // nu stii ce altceva sa pui
//@Repository // manual DB << nu mai folosim pt ca avem SpringData JPA
//@Controller // JSP << NU MAI FOLOSIM
//@RestController //
//@Facade
class B {
   public void method() {
      System.out.println("Hello boot!");
   }
}
