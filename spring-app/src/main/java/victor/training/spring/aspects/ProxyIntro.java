package victor.training.spring.aspects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public class ProxyIntro {
  public static void main(String[] args) {
    Maths real = new Maths();
    Maths decorator = new MathsDecorator(real);
    SecondGrade secondGrade = new SecondGrade(decorator);
    secondGrade.mathClass();
  }
}
class MathsDecorator extends Maths {
  private final Maths delegate;

  MathsDecorator(Maths delegate) {
    this.delegate = delegate;
  }

  @Override
  public int sum(int a, int b) {
    System.out.println("Calling sum with params: " + a + ", " + b);
    return delegate.sum(a, b);
  }

  @Override
  public int product(int a, int b) {
    System.out.println("Calling product with params: " + a + ", " + b);
    return delegate.product(a, b);
  }
}
// TODO: printati param si val returnata de orice metoda chemata din Maths de catre SecondGrade
//  FARA SA MODIFICI CODUL SUB LINIE
// ------------------- LINE ------------------
@Service
@RequiredArgsConstructor
class SecondGrade {
  private final Maths maths;
  public void mathClass() {
    System.out.println("Oare cu cine vorbesc??? " + maths.getClass());
    System.out.println("8 + 4 = " + maths.sum(8, 4));
    System.out.println("6 + 6 = " + maths.sum(6, 6));
    System.out.println("4 x 3 = " + maths.product(4, 3));
  }
}
@Slf4j
@Component
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

