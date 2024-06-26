package victor.training.spring.aspects;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ProxyIntro {
  public static void main(String[] args) {
    Maths real = new Maths();
    Callback callback = new MethodInterceptor() {
      @Override
      public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("Intercepted " + method.getName() + " " + Arrays.toString(args));
        return method.invoke(real, args); // lasa apelul sa se duca la metoda reala
      }
    };
    Maths proxy = (Maths) Enhancer.create(Maths.class, callback);
    SecondGrade secondGrade = new SecondGrade(proxy);
    secondGrade.mathClass();
  }
}

// fara a modifica nimic sub linia asta, printati fiecare
// apel de functie sum/produs cu parametrii ei
// pe care-l face SecondGrade (sa interceptam apelul)
// EXACT O ASTFEL DE CLASA VA GENERA SPRINGUL PENTRU FIECARE BEAN PE CARE VREA SA-L PROXIEZE
//class MathsIntercept extends Maths { // ~ un fel de DECORATOR DESIGN PATTERN
//  private final Maths delegate;
//  MathsIntercept(Maths delegate) {
//    this.delegate = delegate;
//  }
//  public int sum(int a, int b) {
//    System.out.println("Intercepted sum(" + a + ", " + b + ")");
//    return delegate.sum(a, b);
//  }
//
//  public int product(int a, int b) {
//    System.out.println("Intercepted product(" + a + ", " + b + ")");
//    return delegate.product(a, b);
//  }
//}

// -----------------------------------------
@Service
class SecondGrade {
  private final Maths maths;

  SecondGrade(Maths maths) {
    this.maths = maths;
  }

  public void mathClass() {
    System.out.println("8 + 4 = " + maths.sum(8, 4));
    System.out.println("6 + 6 = " + maths.sum(6, 6));
    System.out.println("4 x 2 = " + maths.product(4, 2));
  }
}

@Service
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

