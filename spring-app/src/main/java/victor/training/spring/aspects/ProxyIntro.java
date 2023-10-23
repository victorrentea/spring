package victor.training.spring.aspects;

import io.micrometer.core.annotation.Timed;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.Arrays;

//public class ProxyIntro {
//    public static void main(String[] args) {
//        // WE play the role of Spring here ...
//        Maths real = new Maths();
//    Callback h = new MethodInterceptor() {
//        @Override
//        public Object intercept(Object o, Method method, Object[] params, MethodProxy methodProxy) throws Throwable {
//            System.out.println("Calling " + method.getName() + " with " + Arrays.toString(params));
//            Object r = method.invoke(real, params);
//            System.out.println("==>"+r);
//            return r; // call the method on the real original instance
//        }
//    };
//    Maths proxy = (Maths) Enhancer.create(Maths.class, h); // cglib
//    SecondGrade secondGrade = new SecondGrade(proxy);
//
//        secondGrade.mathClass();
//}
//}
//class MyHack extends Maths {
//    private final Maths delegate;
//    MyHack(Maths delegate) {
//        this.delegate = delegate;
//    }
//    @Override
//    public int sum(int a, int b) {
//        System.out.println("Calling sum with " + a + " " + b);
//        return delegate.sum(a, b);
//    }
//
//    @Override
//    public int product(int a, int b) {
//        System.out.println("Calling product with " + a + " " + b);
//        return delegate.product(a, b);
//    }
//}
// CR: the father wants to audit all the method calls (method call+params) to Maths!!!! to check for errors.
// restriction: don't edit code below the following line

// ------------------------
@Service
class SecondGrade {
    private final Maths maths;
    public SecondGrade(Maths maths) {
        this.maths = maths;
    }

    public void mathClass() {
        System.out.println("Who are you talking to ? " + maths.getClass());

        System.out.println("8 + 4 = " + maths.sum(8, 4));
        System.out.println("6 + 6 = " + maths.sum(6, 6));
        System.out.println("6 + 8 = " + maths.sum(6, 8));
        System.out.println("4 x 3 = " + maths.product(4, 3));
    }
}

@LoggedMethod
@Service
/* #2: final crash*/
class Maths {
//    private Maths() {} // #6
    public /* #4 final ignored*/ int sum(int a, int b) {
        return a + b;
    }

    @Transactional
    public /* #3 static ignored*/ int product(int a, int b) {
        if (a > 2) {
            throw new IllegalArgumentException("Too much!");
        }
        return a * b;
    }
}
