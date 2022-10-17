package victor.training.spring.aspects;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ProxyIntro {
    public static void main(String[] args) {
        // pretend to BE Spring here
        Maths maths = new Maths();
//        Maths proxyDeMana = new Maths() {
//            @Override
//            public int sum(int a, int b) { // override de functie din clasa 'interceptata'
//                System.out.println("Chem sum cu param " + a + ", " + b);
//                return super.sum(a, b);
//            }
//        };
        Callback callback = new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                System.out.println("Chem " + method.getName() + " cu param " + Arrays.toString(args));
                return method.invoke(maths, args);
            }
        };
        Maths mathsProxyCuBytecodeGeneratLaRuntime = (Maths) Enhancer.create(Maths.class, callback);
        SecondGrade secondGrade = new SecondGrade(mathsProxyCuBytecodeGeneratLaRuntime);


        secondGrade.mathClass();
    }
}
 // @Service
class SecondGrade {
    private final Maths maths;

    SecondGrade(Maths maths) {
        this.maths = maths;
    }

    public void mathClass() {
        System.out.println(maths.sum(2, 4));
        System.out.println(maths.sum(1, 5));
        System.out.println(maths.product(2, 3));
    }
}
// @Service
/*final*/ class Maths { // ex runtime
    @Transactional
    public /*final*/ int sum(int a, int b) { // silent ignore!
        // chestii deschizi / inchizi sesiuni
        //
        if (true) {
            throw new IllegalArgumentException("Intentionat");
        }
        return a + b;
    }

    public int product(int a, int b) {
        // chestii

        int produs = 0;
        for (int i = 0; i < a; i++) {
            produs = sum(produs, b); // principalul pitfall din Proxyuri de spring este ca apeluri in cadrul aceleiasi clase NU POT FI INTERCEPTATE.
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
