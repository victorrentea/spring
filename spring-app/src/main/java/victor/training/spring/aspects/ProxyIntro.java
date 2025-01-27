package victor.training.spring.aspects;

public class ProxyIntro {
  public static void main(String[] args) {
    Maths maths = new MathsProxy();
    SecondGrade secondGrade = new SecondGrade(maths);
    secondGrade.mathClass();
  }
}
class MathsProxy extends Maths {
  Maths maths = new Maths();
  public int sum(int a, int b) {
    System.out.println("sum called with " + a + " and " + b);
    return maths.sum(a, b);
  }
  public int product(int a, int b) {
    System.out.println("product called with " + a + " and " + b);
    return maths.product(a, b);
  }
}
// afiseaza param fiecarui apel de metoda in Maths, fara a scrie nimic sub linie
// ------------------- LINIE ------------------
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

