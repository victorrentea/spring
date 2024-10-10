package victor.training.spring.aspects;

import io.micrometer.core.annotation.Timed;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public class ProxyIntro {
  public static void main(String[] args) {
    Maths maths = new Maths();
    SecondGrade secondGrade = new SecondGrade(maths);
    secondGrade.mathClass();
  }
}
// ------------------------ THE LINE ------------------
//CR: we need to LOG the arguments of sum() and product()
class SecondGrade {
  private final Maths maths;
  SecondGrade(Maths maths) {
    this.maths = maths;
  }
  public void mathClass() {
    Util.measure(()->maths.sum(8,4));

    System.out.println("8 + 4 = " + maths.sum(8, 4));
    System.out.println("6 + 6 = " + maths.sum(6, 6));
    System.out.println("4 x 3 = " + maths.product(4, 3));
  }
}
class Maths {
//  @Timed // exposes a metric on how long this method takes at /actuator/promehteus
//  @Transactional // start a tx before and commits/rollback after
//  @Secured("ROLE_CAN_SUM_NUMBERS") // requires the requesting us er to have these roles
  public int sum(int a, int b) {
    transaction.start
    try{
      long t0 = System.currentTimeMillis();
      System.out.println("Sum " + a + " and " + b);
      return a + b;
      long t1 = System.currentTimeMillis();
    transaction.commit
     }catch(Ex) { transaction.rollback }
  }
  public int product(int a, int b) {
    System.out.println("Product " + a + " and " + b);
    return a * b;
  }
}


// Key Points
// - Class Proxy using CGLIB Enhancer to extend the proxied class
// - Proxy limitations: final methods/classes, local calls
// - Debug a Proxy
// - Custom @Aspect

