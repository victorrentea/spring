package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

public class ProxyIntro {
  public static void main(String[] args) {
    // WE play the role of Spring here ...
    Maths maths = new Maths();
    SecondGrade secondGrade = new SecondGrade(maths);
    secondGrade.mathClass();
  }
}
// ------------------------ THE LINE ------------------
class SecondGrade {
  private final Maths maths;
  SecondGrade(Maths maths) {
    this.maths = maths;
  }
  public void mathClass() {
    System.out.println("8 + 4 = " + maths.sum(8, 4));
    System.out.println("6 + 6 = " + maths.sum(6, 6));
    System.out.println("4 x 3 = " + maths.product(4, 3));
  }
}
// TODO: orice apel catre Maths sa logeze parametrii
@Slf4j
class Maths {
  public int sum(int a, int b) {
    log.info("sum ({},{})", a, b);
    return a + b;
  }
  public int product(int a, int b) {
    log.info("product ({},{})", a, b);
    return a * b;
  }
}

