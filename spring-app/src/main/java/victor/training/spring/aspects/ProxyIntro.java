package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(ProxyIntro.class);
    public static void main(String[] args) {
        // Play the role of Spring here ...
        Maths realInstance = new Maths();

        Callback h = new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] params, MethodProxy methodProxy) throws Throwable {
                log.info("Apelez "+ method.getName() +" cu param " + Arrays.toString(params));
                return method.invoke(realInstance, params);
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
//class Wrapper extends  Maths {
//    private static final Logger log = LoggerFactory.getLogger(ProxyIntro.class);
//    private final Maths maths;
//
//    public Wrapper(Maths maths) {
//        this.maths = maths;
//    }
//
//    @Override
//    public int sum(int a, int b) {
//        log.info("{}+{}",a,b);
//        return maths.sum(a, b);
//    }
//
//    @Override
//    public int product(int a, int b) {
//        log.info("{}+{}",a,b);
//        return maths.product(a, b);
//    }
//}
// codu din framework
// --------------
// codu app mele
// -------------------------------------
@Service
class SecondGrade {
    private final Maths maths;
    SecondGrade(Maths maths) { // spring nu va injecta clasa REALA Maths ci un wrapper peste.o clasa care extinde pe-a mea!!
//        this.maths = new Maths(); // tzapa #1 : new
        this.maths =maths;
    }
    public void mathClass() {
        System.out.println("2 + 4 = " + maths.sum(2, 4));
        System.out.println("1 + 5 = " + maths.sum(1, 5));
        System.out.println("2 x 3 = " + maths.product(2, 3));
    }
}

@Slf4j
@Facade
//final // #2 crash at runtime
class Maths {
    public  /*final*/ int sum(int a, int b) { // #3 skips the method
        return a + b;
    }

    public int product(int a, int b) {
        int produs = 0;
        for (int i = 0; i < a; i++) {
            produs = sum(produs, b); // #4 cea mai cea tzapa: apelurile locale nu sunt proxiate!!!!!!!!!!!!!!!!!!
        }
        return produs;
//        return a * b;
    }
}


// Key Points
// [2] Class Proxy using CGLIB (Enhancer) extending the proxied class
// [3] Spring Cache support [opt: redis]
// [4] Custom @Aspect, applied to methods in @Facade
// [6] Tips: self proxy, debugging, final
// [7] OPT: Manual proxying using BeanPostProcessor
