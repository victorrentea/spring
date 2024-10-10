package victor.training.spring.aspects;

public class ProxyIntro {
  public static void main(String[] args) {
    Maths maths = new Maths();
    SecondGrade secondGrade = new SecondGrade(maths);
    secondGrade.mathClass();
  }
}

//CR: we need to LOG the arguments of sum() and product()
// WITHOUT TOUCHING ANY CODE BELOW THE LINE
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

class Maths {
  public int sum(int a, int b) {
    System.out.println("Sum " + a + " and " + b);
    return a + b;
  }

  public int product(int a, int b) {
    System.out.println("Product " + a + " and " + b);
    return a * b;
  }
}


//  @Timed // exposes a metric on how long this method takes at /actuator/promehteus
//  @Transactional // start a tx before and commits/rollback after
//  @Secured("ROLE_CAN_SUM_NUMBERS") // requires the requesting us er to have these roles
// Key Points
// - Class Proxy using CGLIB Enhancer to extend the proxied class
// - Proxy limitations: final methods/classes, local calls
// - Debug a Proxy
// - Custom @Aspect

