package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.ap.internal.model.Decorator;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;


public class ProxyIntro {
    public static void main(String[] args) {
        // WE play the role of Spring here ...
        Maths realInstance = new Maths();

        Callback h = new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                System.out.println(method.getName() + " with " + Arrays.toString(args));
                Object r = method.invoke(realInstance, args);
                System.out.println("returned " + r);
                return r;
            }
        };
        Maths proxy = (Maths) Enhancer.create(Maths.class, h);

        SecondGrade secondGrade = new SecondGrade(proxy);

        secondGrade.mathClass();
    }
}
//@Slf4j
//class Decorator extends Maths { // proxy instance is a SEPARATE instance from
//    // your real bean instance.
//    private final Maths delegate;
//    Decorator(Maths delegate) {
//        this.delegate = delegate;
//    }
//    @Override
//    public int sum(int a, int b) {
//        log.info("Calling sum(" + a + "," + b + ")");
//        return delegate.sum(a, b);
//    }
//    @Override
//    public int product(int a, int b) {
//        log.info("Calling product(" + a + "," + b + ")");
//        return delegate.product(a, b);
//    }
//}

// TODO write code above this line to intercept and log args of any call that SecondGrade does on Maths
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

