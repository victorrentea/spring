package victor.training.spring.aspects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public class ProxyIntro {
  public static void main(String[] args) {
    // WE play the role of Spring here ...
    Maths maths = new Maths();
    SecondGrade secondGrade = new SecondGrade(maths);
    secondGrade.mathClass();
  }
}
// Requirement: log the arguments of Maths#sum() and Maths#product() methods
// ------------------- LINE ------------------
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
class Maths {
  private static final Logger log = LoggerFactory.getLogger(Maths.class);

  public int sum(int a, int b) {
    log.info("sum ({},{})", a, b);
    return a + b;
  }
  public int product(int a, int b) {
    log.info("product ({},{})", a, b);
    return a * b;
  }
}
