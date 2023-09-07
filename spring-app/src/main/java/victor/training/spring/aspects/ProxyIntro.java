package victor.training.spring.aspects;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.ap.internal.model.Decorator;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

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
        Maths proxy = (Maths) Enhancer.create(Maths.class, h); // does not work on native image

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
// TODO what can I change below the line to STOP proxies from working
@Service
class SecondGrade {
    private final Maths maths;
    SecondGrade(Maths maths) {
        this.maths = maths;
    }

    public void mathClass() {
//        Maths maths = new Maths(); // no proxying can happen if you don't inject a dep from DI container
        System.out.println("8 + 4 = " + maths.sum(8, 4));
        System.out.println("6 + 6 = " + maths.sum(6, 6));
        System.out.println("4 x 3 = " + maths.product(4, 3));
    }
}
@Service
/*final */
@LoggedMethod
class Maths { // break startup EXCEPTION
//    private  Maths() {} // too much
//    @Transactional // START tx before enter, COMMIT after if no exception

//    @Cacheable("sum-cache") // if i've computed sum( same args), return the prev value
//    @Secured("ROLE_ADMIN") // only ADMIN role can call it

//    @Async // move the ex on a different thread

//    @Retry(name = "sum") // if a certain exception is thrown : retry dep on some config
//    @RateLimiter() // max 5 req/sec
//    @Bulkhead() // max 5 requests at once (overlapped)
//    @CircuitBreaker() // if that system blows, back off for 3 sec and provide this default response..
    public /*static*/ int sum(int a, int b) { // non-overridable method
        // Aspects have programatic alternatives
        //        TransactionTemplate tx;
//        tx.executeWithoutResult(s-> {
//            repo.save(new A());
//            repo.save(new B());
//        });
//        MeterRegistry micrometer;
//        micrometer.timer("a").record(() -> {stuff});
        return a + b;
    }

    public /*final*/ int product(int a, int b) { // ignore this method, no exception at startup
//        return a * b;
        int product = 0;
        for (int i = 0; i < a; i++) {
            product = sum(product, b); // ⚠️ local method calls (on "this") cannot be proxied/intercepted by Spring
        }
        return product;
    }
}


// Key Points
// - Class Proxy using CGLIB Enhancer to extend the proxied class
// - Proxy limitations: final methods/classes, local calls
// - Debug a Proxy
// - Custom @Aspect

