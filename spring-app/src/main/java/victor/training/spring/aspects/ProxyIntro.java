package victor.training.spring.aspects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Arrays;

@SpringBootApplication
public class ProxyIntro {
    public static void main(String[] args) {
        // pretend to BE Spring here - doing DI
        Maths maths = new Maths() /*{ // anonymous extends
            @Overrid
            public int sum(int a, int b) {
                System.out.println("calling sum with  " + a + " and " + b);
                return super.sum(a, b);
            }
        }*/;

        Callback h = new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                System.out.println("Calling " + method.getName() + " with args " + Arrays.toString(args));

               // check cache and return from cache
                // start tx
                // check roles
                Object result = method.invoke(maths, args);
                System.out.println("Returned " + result);
                return result;
            }
        };
        Maths mathsProxy = (Maths) Enhancer.create(Maths.class, h);

        SecondGrade secondGrade = new SecondGrade(mathsProxy);
        // TODO because my daughter has a maniac father, the father wants to intercept all calls to
        // maths methods and their results, to check them
        new ProxyIntro().run(secondGrade);
    }

//    //    public static void main(String[] args) {SpringApplication.run(ProxyIntro.class, args);}
//
    @Autowired
    public void run(SecondGrade secondGrade) {
        System.out.println("At runtime...");
        secondGrade.mathClass();
    }

}
// spring under the hood   "decorator pattern py"
// @g
// f()
// ---------------- line -------------------------------
// application code

@Service
class SecondGrade {
    private final Maths maths;

    SecondGrade(Maths maths) {
        this.maths = maths;
    }

    public void mathClass() {
        System.out.println("You are not injected the REAL Maths class, but a proxy");
        System.out.println("You tell it's a proxy if you see $$ Enhancer: " + maths.getClass());
        System.out.println(maths.sum(2, 4));
        System.out.println(maths.sum(1, 5));
        System.out.println(maths.product(2, 3));
    }
}

//@Facade
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
// [2] Class Proxy using CGLIB (Enhancer) extending the proxied class
// [3] Spring Cache support [opt: redis]
// [4] Custom @Aspect, applied to methods in @Facade
// [6] Tips: self proxy, debugging, final
// [7] OPT: Manual proxying using BeanPostProcessor
