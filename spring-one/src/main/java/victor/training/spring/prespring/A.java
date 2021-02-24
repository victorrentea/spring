package victor.training.spring.prespring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import victor.training.spring.prespring.sub.B;

@Service
//@RequiredArgsConstructor // dovada ca limbajul Java are 25 de ani vechime
public class A implements CommandLineRunner {
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
