package victor.training.spring.aspects;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ProxyIntro {
    public static void main(String[] args) {
        // WE play the role of Spring here ...
//        Maths maths = new Maths() {
//            @Override
//            public int sum(int a, int b) {
//                int result = super.sum(a, b); // real call
////                System.out.println("sum \{a} + \{b} = \{result}"); // java24
//                System.out.println("SRI: sum "+ a+ " + " + b +" = "+result); // java21
//                return result;
//            }
//        };
        Maths realObj = new Maths();

        Callback h = new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

                // ce face springul cu acest mecanism (think: adnotari pe metode)?
                // - logging daca vrei tu cu @Aspect
                // - @Bean sa subclaseze @Configuration
                // - @Transactional start ,,,,,, commit/rollback
                // - @Validated
                // - @Cacheable
                // - @Secured/@PreAuthorize
                // -

                Object result = method.invoke(realObj, args);

                System.out.println("SRI: " + method.getName() + " " + Arrays.toString(args) + " = " + result);
                return result;
            }
        };
        Maths proxy = (Maths) Enhancer.create(Maths.class, h);

//        Maths proxy = new MathsProxy(realObj);
        SecondGrade secondGrade = new SecondGrade(proxy);
        // TODO logeaza param si rezultatele tutoro metodelor din Maths chemate de SecondGrade
        // -- pana aici ar face spring in startup
        // la runtime
        secondGrade.mathClass();
    }
}

//@RequiredArgsConstructor
//class MathsProxy extends Maths { // ~ decorator pattern
//    private final Maths realObject;
//
//    @Override
//    public int sum(int a, int b) {
//        int result = realObject.sum(a, b); // delegate to YOUR method
//        System.out.println("SRI: sum "+ a+ " + " + b +" = "+result);
//        return result;
//    }
//
//    @Override
//    public int product(int a, int b) {
//        int result = realObject.product(a, b); // delegate to YOUR method
//        System.out.println("SRI: product "+ a+ " + " + b +" = "+result);
//        return result;
//    }
//}

// ------------------------  n-ai voie sa scrii nimic sub linia asta: fii-mea nu tre sa afle --------
@Service
class SecondGrade {
    private final Maths maths;
    SecondGrade(Maths maths) {
        this.maths = maths;
    }

    public void mathClass() {
        System.out.println("Oare pe ce object chem eu .sum ? " +maths.getClass());
        System.out.println("8 + 4 = " + maths.sum(8, 4));
        System.out.println("6 + 6 = " + maths.sum(6, 6));
        System.out.println("4 x 3 = " + maths.product(4, 3));
    }
}
@Facade
class Maths {
    public Integer sum(int a, int b) {
        System.out.println("executa?");
        return a + b;
    }
    @LoggedMethod
    @Cacheable("copiutza")
    @Timed("produs")
    public int product(int a, int b) {
        new RuntimeException().printStackTrace();
        return a * b;
    }
}


// Key Points
// - Class Proxy using CGLIB Enhancer to extend the proxied class
// - Proxy limitations: final methods/classes, local calls
// - Debug a Proxy
// - Custom @Aspect

