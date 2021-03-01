package victor.training.spring.intro;

import org.springframework.stereotype.Component;

@Component
public class B {
   public int method() {
      throw new IllegalArgumentException();
   }
}
