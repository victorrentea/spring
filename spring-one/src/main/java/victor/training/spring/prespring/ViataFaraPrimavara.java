package victor.training.spring.prespring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class ViataFaraPrimavara {

   public static void main(String[] args) {
       SpringApplication.run(ViataFaraPrimavara.class, args);
   }

}
@Service
class A implements CommandLineRunner {
   public A() {
      System.out.println("ALFA");
   }

   @Autowired
   private B b; // 50-100 de linii de XML iti injectai o instanta de B manageuita de Application Server (EJB container).



   public void method() {
      // logica
      b.isPrime(3);
      b.isPrime(10_000_169);
      // logica
   }

   @Override
   public void run(String... args) throws Exception {
      System.out.println("Perfect pt teste si joaca : " + b);
   }
}

@Service
class B {
   public boolean isPrime(int n) {
      // ASDSAD
      // ASDSAD
      return true;
   }
}