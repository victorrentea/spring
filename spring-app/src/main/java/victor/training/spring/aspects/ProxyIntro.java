package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
public class ProxyIntro {
    public static void main(String[] args) {
        // pretend to BE Spring here
        Maths real = new Maths();
        Callback h = new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                log.info(method.getName() + " of " + Arrays.toString(args));
                return method.invoke(real, args);
            }
        };
        Maths maths = (Maths) Enhancer.create(Maths.class, h);
        SecondGrade secondGrade = new SecondGrade(maths);

        // TODO maniac father wants to log all the math operations done during school
        secondGrade.mathClass();
    }

}

class SecondGrade {
    private final Maths maths;

    SecondGrade(Maths maths) {
        this.maths = maths;
    }

    public void mathClass() {
        System.out.println("Who am I talking to really here? " + maths.getClass());
        System.out.println(maths.sum(2, 4));
        System.out.println(maths.sum(1, 5));
        System.out.println(maths.product(2, 3));
        System.out.println(maths.product(3, 3));
    }
}

@Slf4j
/*final*/ class Maths { // explosion
    public int sum(int a, int b) {
        return a + b;
    }

    public /*final*/ int product(int a, int b) { // silently skip proxying this method. DON'T USE
//        return a * b;

        int prod = 0;
        for (int i = 0; i < a; i++) {
            prod = sum(prod, b); // NEVER audited because is NOT going through the proxy.
        }
        return prod;

    }
}


// Key Points
// [2] Class Proxy using CGLIB (Enhancer) extending the proxied class
// [3] Spring Cache support [opt: redis]
// [4] Custom @Aspect, applied to methods in @Facade
// [6] Tips: self proxy, debugging, final
// [7] OPT: Manual proxying using BeanPostProcessor
