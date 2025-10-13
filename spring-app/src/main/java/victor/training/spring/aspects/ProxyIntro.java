package victor.training.spring.aspects;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public class ProxyIntro {
  public static void main(String[] args) {
    Maths maths = new NamingIsHardCaIncaHabarNAmCeFacAici_Maths();
    SecondGrade secondGrade = new SecondGrade(maths);
    secondGrade.mathClass();
  }
}
class NamingIsHardCaIncaHabarNAmCeFacAici_Maths extends Maths {
  @Override
  public int sum(int a, int b) {
    System.out.println("sum called with " + a + " and " + b);
    return super.sum(a, b);
  }
  @Override
  public int product(int a, int b) {
    System.out.println("product called with " + a + " and " + b);
    return super.product(a, b);
  }
}
// logeaza param primiti de metodele din Maths fara sa modifici cod sub linie
// sau: ti-e #rusine sa copii cod de 7 ori
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
  public int sum(int a, int b) {
    return a + b;
  }
  public int product(int a, int b) {
    return a * b;
  }
}


// Key Points
// - Class Proxy using CGLIB Enhancer to extend the proxied class
// - Proxy limitations: final methods/classes, local calls
// - Debug a Proxy
// - Custom @Aspect

