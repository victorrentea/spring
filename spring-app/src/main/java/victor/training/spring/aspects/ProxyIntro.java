package victor.training.spring.aspects;

public class ProxyIntro {
  public static void main(String[] args) {
    Maths maths = new Maths();
    SecondGrade secondGrade = new SecondGrade(maths);

    secondGrade.mathClass();
  }
}

// ------------------------
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
  // TODO sa logam orice operatie face Maths
  public int sum(int a, int b) {
    System.out.println("sum(" + a + "," + b + ")");
    return a + b;
  }

  public int product(int a, int b) {
    System.out.println("product(" + a + "," + b + ")");
    return a * b;
  }
}
