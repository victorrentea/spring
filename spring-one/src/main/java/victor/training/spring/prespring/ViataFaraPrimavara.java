package victor.training.spring.prespring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class ViataFaraPrimavara {

   public static void main(String[] args) {
       SpringApplication.run(ViataFaraPrimavara.class, args);
   }

}

@Service
class AltaClasa {
   private final B b;

   AltaClasa(B b) {
      this.b = b;
   }
   @PostConstruct
   public void init() {
      // poti sa verifici de ex ca un folder  de IN exista pe disc si ca ai chmod pe el
      // poti sa anunti un alt sistem ca te-ai trezit
      // poti sa validezi niste proprietati e sistem / de spring (de ex verificand ca un URL de serviciu web e accesibil)
      System.out.println(b);
   }
}


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





@RestController
class C {
   @GetMapping("/api/call1")
   public void method() {

   }
}
