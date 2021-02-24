package victor.training.spring.prespring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class ViataFaraPrimavara {

   public static void main(String[] args) {
       SpringApplication.run(ViataFaraPrimavara.class, args);
      B mockB = new B();
       new A(mockB, c);
   }

}
@Service class C {}

@Service
//@RequiredArgsConstructor // dovada ca limbajul Java are 25 de ani vechime
class A implements CommandLineRunner {
   private final B b; // 50-100 de linii de XML iti injectai o instanta de B manageuita de Application Server (EJB container).

   public A(B b) {
      this.b = b;
   }

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