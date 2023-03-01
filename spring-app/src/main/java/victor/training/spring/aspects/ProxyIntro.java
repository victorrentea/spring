package victor.training.spring.aspects;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.Arrays;

@SpringBootApplication
public class ProxyIntro {
    public static void main(String[] args) {
        // Play the role of Spring here ...

        Maths maths = new Maths();

//        Maths proxy = new MathsProxy(maths);
        Callback h = new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("Calling method " + method.getName() + " with args " + Arrays.toString(objects));
                return method.invoke(maths, objects);
            }
        };
        Maths proxy = (Maths) Enhancer.create(Maths.class, h);

        SecondGrade secondGrade = new SecondGrade(proxy);
        new ProxyIntro().run(secondGrade);
    }
    //    public static void main(String[] args) {SpringApplication.run(ProxyIntro.class, args);}
    //@Autowired // uncomment to run in Spring
    public void run(SecondGrade secondGrade) {
        System.out.println("Running Maths class...");
        secondGrade.mathClass();
    }
}

// my daughter has maniac father that wants to check all the math operations performed during the day
// without her knowing: print out param and return val of every method in Maths called by second grade.
//class MathsProxy extends Maths {
//    private final Maths delegate;
//
//    MathsProxy(Maths delegate) {
//        this.delegate = delegate;
//    }
//
//    @Override
//    public int sum(int a, int b) {
//        System.out.println("Calling sum " + a + ", " + b);
//        return delegate.sum(a, b);
//    }
//    @Override
//    public int product(int a, int b) {
//        System.out.println("Calling product " + a + ", " + b);
//        return delegate.product(a, b);
//    }
//}

// (framework)
// -------------------------------------------
// (application code)
// don;t change any code below the line

class SecondGrade {
    private final Maths maths;

    SecondGrade(Maths maths) {
        this.maths = maths;
    }

    public void mathClass() {
        System.out.println("2 + 4 = " + maths.sum(2, 4));
        System.out.println("1 + 5 = " + maths.sum(-1, 5));
        System.out.println("2 x 3 = " + maths.product(2, 3));
    }
}

/*final crash*/
class Maths {
    @Transactional // the proxy that this annotation summons never plays if the method is called locally
    // (ie in the same class)
    public /*static ignored cannot be @Override*/ int sum(int a, int b) {
        return a + b;
    }

    public /*final ignored*/ int product(int a, int b) {
//        return a * b;

        int result = 0;
        for (int i = 0; i < a; i++) {
            result = sum(result, b); // THE MOST common pitfall of proxies:
            // they don't play if you call that method in the same class
        }
        return result;
    }
}


// Key Points
// [2] Class Proxy using CGLIB (Enhancer) extending the proxied class
// [3] Spring Cache support [opt: redis]
// [4] Custom @Aspect, applied to methods in @Facade
// [6] Tips: self proxy, debugging, final
// [7] OPT: Manual proxying using BeanPostProcessor
