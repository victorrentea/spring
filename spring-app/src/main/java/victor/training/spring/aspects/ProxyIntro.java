package victor.training.spring.aspects;

public class ProxyIntro {
  public static void main(String[] args) {
    Maths maths = new MathsSubclass();
    SecondGrade secondGrade = new SecondGrade(maths);

    secondGrade.mathClass();
  }
}
class MathsSubclass extends Maths {
  @Override
  public int sum(int a, int b) {
    System.out.println("sum(" + a + "," + b + ")");
    return super.sum(a, b);
  }
  @Override
  public int product(int a, int b) {
    System.out.println("product(" + a + "," + b + ")");
    return super.product(a, b);
  }
}

// TODO sa logam orice operatie face Maths, dar nu ai voie sa scrii cod sub linia asta:
// -------------------------------------------------------------
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
