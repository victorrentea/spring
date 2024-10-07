package victor.training.spring.first;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Service
@Component
public class AnotherClassInTheSamePackage {
  public AnotherClassInTheSamePackage() {
    System.out.println("NEW INSTANCE OF " + getClass().getSimpleName());
  }
}
