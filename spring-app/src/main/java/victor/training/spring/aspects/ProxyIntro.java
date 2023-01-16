package victor.training.spring.aspects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class ProxyIntro {


  public static void main(String[] args) {
    SpringApplication.run(ProxyIntro.class, args);
  }

  @Autowired
  public void run(SecondGrade secondGrade) {
    System.out.println("At runtime...");
    secondGrade.mathClass();
  }

  //    public static void main(String[] args) {
  //        // pretend to BE Spring here
  //        Maths maths = new Maths();
  //        // the real story: spring face asa :
  //        Callback h = new MethodInterceptor() {
  //            @Override
  //            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
  //                System.out.println("Cheama fiimea metoda " + method.getName() + " cu param " + Arrays.toString(args));
  //                return method.invoke(maths, args);
  //            }
  //        };
  //        Maths proxy = (Maths) Enhancer.create(Maths.class, h);
  //        SecondGrade secondGrade = new SecondGrade(proxy);
  //        new ProxyIntro().run(secondGrade);
  //    }
}

//class TataManiac extends Maths {
//    private final Maths original;
//
//    TataManiac(Maths original) {
//        this.original = original;
//    }
//
//    @Override
//    public int sum(int a, int b) {
//        System.out.println("log");
//        return original.sum(a, b);
//    }
//
//    @Override
//    public int product(int a, int b) {
//        System.out.println("log");
//        return original.product(a, b);
//    }
//}
////-------------------------------------------
@RequiredArgsConstructor
@Service
class SecondGrade {
  private final Maths maths; // Congratulations, it's a proxy!

  public void mathClass() {
    System.out.println("Oare cu cine vorbesc?!?! " + maths.getClass());
    System.out.println(maths.sum(2, 4));
    System.out.println(maths.sum(1, 5));
    System.out.println(maths.sum(2, 5));
    System.out.println(maths.product(2, 3));
  }
}

@Facade
@Slf4j
        /*final crapa*/ class Maths {
  //    @Transactional
  //    @Secured("ADMIN")
  public int sum(int a, int b) {
    return a + b;
  }

  public int product(int a, int b) {
    //        return a * b;
    int r = 0;
    for (int i = 0; i < a; i++) {
      r = sum(r, b);
      // apelurile locale de metode nu trec prin proxy-uri!! - cel mai criminala capcana de la proxy-uri in Sprng
      // adica (RO): daca ai adnotari pe acea metoda ele nu vor mai face nimic daca e chema din aceeasi clasa.
    }
    return r;
  }
}


// Key Points
// [2] Class Proxy using CGLIB (Enhancer) extending the proxied class
// [3] Spring Cache support [opt: redis]
// [4] Custom @Aspect, applied to methods in @Facade
// [6] Tips: self proxy, debugging, final
// [7] OPT: Manual proxying using BeanPostProcessor
