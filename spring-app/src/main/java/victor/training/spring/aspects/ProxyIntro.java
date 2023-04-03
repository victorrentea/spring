package victor.training.spring.aspects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Arrays;

//@SpringBootApplication
public class ProxyIntro {
    public static void main(String[] args) {
        // Play the role of Spring here ...
        Maths realImpl = new Maths();
        Callback h = new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] arguments, MethodProxy methodProxy) throws Throwable {
                System.out.println("Intercepting " + method.getName() + " with params " + Arrays.toString(arguments));
                Object r = method.invoke(realImpl, arguments);
                System.out.println("Result: " + r);
                return r;
            }
        };
        Maths classProxy = (Maths) Enhancer.create(Maths.class, h);

        SecondGrade secondGrade = new SecondGrade(classProxy);
        new ProxyIntro().run(secondGrade);
    }

    //    public static void main(String[] args) {SpringApplication.run(ProxyIntro.class, args);}

    //@Autowired // uncomment to run in Spring
    public void run(SecondGrade secondGrade) {
        System.out.println("Running Maths class...");
        secondGrade.mathClass();
    }
}
//class MathProxy extends Maths {
//    @Override
//    public int sum(int a, int b) {
//        int r = super.sum(a, b);
//        System.out.println(" sum(" + a + ", " + b + ") = " + r);
//        return r;
//    }
//
//    @Override
//    public int product(int a, int b) {
//        return super.product(a, b);
//    }
//}

// -- a line --------------------------------------------
// you are not allowed to touch any code bellow this line
//@Service
class SecondGrade {
    private final Maths maths;

    SecondGrade(Maths maths) {
        this.maths = maths;
    }

    public void mathClass() {
        System.out.println("Who am I talking to? " + maths.getClass().getName());
        System.out.println("2 + 4 = " + maths.sum(2, 4));
        System.out.println("1 + 5 = " + maths.sum(1, 5));
        System.out.println("2 x 3 = " + maths.product(2, 3));
        System.out.println("4 x 3 = " + maths.product(4, 3));
    }
}

//@Facade
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
