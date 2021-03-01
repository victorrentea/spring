package victor.training.spring.intro;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class A {
   private final B b;
   private final C c;

   public int method() {
      //mm
      return b.method() + 1;
      //logica
   }

   @PostConstruct
   public void init() {
      System.out.println("Aa");
   }
}

@Service
class C {

}