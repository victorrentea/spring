package victor.training.spring.aspects;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;

@SpringBootApplication
@Slf4j
public class ProxyIntro {
//    public static void main(String[] args) {
//        // pretend to BE Spring here
//        Maths maths = new Maths();
////        Decorator decorator = new Decorator(maths);
//        Callback h = new MethodInterceptor() {
//            @Override
//            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
//                Object r = method.invoke(maths, objects);
//                log.info("calling {} with {} returned {}",method.getName(), List.of(objects), r);
//                return r;
//            }
//        };
//        Maths proxy = (Maths) Enhancer.create(Maths.class, h);
//        SecondGrade secondGrade = new SecondGrade(proxy);
//        new ProxyIntro().run(secondGrade);
//    }
        public static void main(String[] args) {SpringApplication.run(ProxyIntro.class, args);}
    @Autowired
    public void run(SecondGrade secondGrade) {
        System.out.println("At runtime...");
        secondGrade.mathClass();
    }
}

//@Slf4j
//class Decorator extends Maths {
//    private final Maths maths;
//    public Decorator(Maths maths) {
//        this.maths = maths;
//    }
//    public int sum(int a, int b) {
//        int r = maths.sum(a, b);
//        log.info("calling sum with {} returned {}", List.of(a,b), r);
//        return r;
//    }
//    public int product(int a, int b) {
//        int r = maths.product(a, b);
//        log.info("calling product with {} returned {}", List.of(a,b), r);
//        return r;
//    }
//
//}

// =========a line. you can't touch anything under this line -=-=====
@Service
@RequiredArgsConstructor
class SecondGrade {
    private final Maths maths;
    public void mathClass() {
        System.out.println(maths.sum(2, 4));
        System.out.println(maths.sum(1, 5));
        System.out.println(maths.product(2, 3));
    }
}

@Facade
/*final breaks startup*/ class Maths {
    public /*final silently ignored*/ int sum(int a, int b) {
        return a + b;
    }
    @LoggedMethod
    public int product(int a, int b) {
//        return a * b;

        int product = 0;
        for (int i = 0; i < a; i++) {
            product = sum(product, b); // you call here the ORIGINAL sum() method,
            // there is no proxy between you and your local method
        }
        return product;
    }
}


// Key Points
// [2] Class Proxy using CGLIB (Enhancer) extending the proxied class
// [3] Spring Cache support [opt: redis]
// [4] Custom @Aspect, applied to methods in @Facade
// [6] Tips: self proxy, debugging, final
// [7] OPT: Manual proxying using BeanPostProcessor
