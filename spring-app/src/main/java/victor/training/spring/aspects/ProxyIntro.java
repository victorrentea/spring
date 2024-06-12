package victor.training.spring.aspects;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ProxyIntro {
    public static void main(String[] args) {
        // WE play the role of Spring here ...
//        Maths maths = new MathProxy(new Maths()); // a-la DECORATOR pattern
        Maths real= new Maths();
        MethodInterceptor callback = new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
//                if (!) thorw
                System.out.println("Calling " + method.getName() + " with " + Arrays.toString(args));
                Object r = method.invoke(real, args);
                System.out.println("Result: " + r);
                return r;
            }
        };
        Maths proxy = (Maths) Enhancer.create(Maths.class, callback);
        SecondGrade secondGrade = new SecondGrade(proxy);
        secondGrade.mathClass();
    }
}
// Spring va genera asta:
//class MathProxy extends Maths {
//    private final Maths maths;
//    MathProxy(Maths maths) {
//        this.maths = maths;
//    }
//
//    @Override
//    public int sum(int a, int b) {
//        System.out.println("Calling sum with " + a + " and " + b);
//        return maths.sum(a, b);
//    }
//
//    @Override
//    public int product(int a, int b) {
//        System.out.println("Calling product with " + a + " and " + b);
//        return maths.product(a, b);
//    }
//}
// TODO aici
// scriind doar deasupra acestei linii, printeaza pe System.out orice apel
// la sum sau produs cu param dati
// ------------------------
@Service
class SecondGrade {
    private final Maths maths; // spring a injectat aici un proxy
    SecondGrade(Maths maths) {
        this.maths = maths;
    }
    public void mathClass() {
//        maths = new Maths(); // nu mai e proxy
        System.out.println(maths.getClass().getName());
        System.out.println("8 + 4 = " + maths.sum(8, 4));
        System.out.println("6 + 6 = " + maths.sum(6, 6));
        System.out.println("5 + 5 = " + maths.sum(5, 5));
        System.out.println("4 x 3 = " + maths.product(4, 3));
    }
}
@Service
/*final*/ class Maths { // crapa la startup
//    @Secured("ROLE_ADMIN") sau @Transactional  NU MERG!!!!
    public /*final sau static*/ int sum(int a, int b) { // ignora metoda
        return a + b;
    }
    public  int product(int a, int b) {
        int r = 0;
        for (int i = 0; i < b; i++) {
            r = sum(r, a); // 👑 apelul asta nu a fost interceptat de proxy. E un apel local.
        }
        return r;
    }
}


// Key Points
// - Class Proxy using CGLIB Enhancer to extend the proxied class
// - Proxy limitations: final methods/classes, local calls
// - Debug a Proxy
// - Custom @Aspect

