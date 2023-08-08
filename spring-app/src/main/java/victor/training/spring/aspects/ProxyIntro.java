package victor.training.spring.aspects;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ProxyIntro {
    public static void main(String[] args) {
        // WE play the role of Spring here ...
        Maths realMathsBeanInstance = new Maths();
//        Maths maths = new SubClassOfMaths();
        Callback h = new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

//                try {
//                    transaction.start
                    Object r = method.invoke(realMathsBeanInstance, args);
//                    transaction.commit
//                }catch() {
//                    transaction.rollback
//                }
//                method.getAnnotation(Transactional.class).is
                System.out.println(method.getName() + " (" + Arrays.toString(args) + ")=" + r);
                return r;
            }
        };
        Maths proxy = (Maths) Enhancer.create(Maths.class, h);
        SecondGrade secondGrade = new SecondGrade(proxy);

        secondGrade.mathClass();
    }
}
//class SubClassOfMaths extends Maths {
//    @Override
//    public int sum(int a, int b) {
//        int r = super.sum(a, b);
//        System.out.println("Sum (" + a + ", " + b + ")=" + r);
//        return r;
//    }
//
//    @Override
//    public int product(int a, int b) {
//        int r = super.product(a, b);
//        System.out.println("Product (" + a + ", " + b + ")=" + r);
//        return r;
//    }
//}
// TODO without my daughter finding out (ie writing code only above the line)
//  make sure you keep track of all the sum and product operations she did  (log them)
// ---------------------------
@Service
class SecondGrade {
    private final Maths maths;
    SecondGrade(Maths maths) {
        this.maths = maths;
    }

    public void mathClass() {
        System.out.println("Maths instance I got is: " + maths.getClass());
        System.out.println("8 + 4 (new) = " + new Maths().sum(8, 4)); // not proxied
        System.out.println("8 + 4 = " + maths.sum(8, 4));
        System.out.println("6 + 6 = " + maths.sum(6, 6));
        System.out.println("4 x 3 = " + maths.product(4, 3));
    }
}
@Facade
/*final*/ class Maths { // fail to start
    @LoggedMethod
//    @Transactional @Async @Secured() @Timed @RateLimiter @Retry() @Cacheable
//    public static int sum(int a, int b) { // just THIS method is ignored
    public /*final*/ int sum(int a, int b) { // just THIS method is ignored
        return a + b;
    }
    @LoggedMethod
    public int product(int a, int b) {
        // 6 x 5 = 6 + 6 + 6 + 6 + 6
        int result = 0;
        for (int i = 0; i < a; i++) {
            result = sum(result, b); // local method calls (within the same class)
            // are NOT intercepted by spring AOP
        }
        return result;
    }
}


// Key Points
// - Class Proxy using CGLIB Enhancer to extend the proxied class
// - Proxy limitations: final methods/classes, local calls
// - Debug a Proxy
// - Custom @Aspect

