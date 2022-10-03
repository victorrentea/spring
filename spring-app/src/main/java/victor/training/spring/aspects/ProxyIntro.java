package victor.training.spring.aspects;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ProxyIntro {
    public static void main(String[] args) {
        // pretend to BE Spring here
        Maths mathsReal = new Maths();


        Callback callback = new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                System.out.println(" Pt tata : " + method.getName() + " cu " + Arrays.toString(args));
                return method.invoke(mathsReal, args);
            }
        };
        Maths mathsProxy = (Maths) Enhancer.create(Maths.class, callback);

        SecondGrade secondGrade = new SecondGrade(mathsProxy);

        secondGrade.mathClass();
    }

}

class SecondGrade {
    private final Maths maths;

    SecondGrade(Maths maths) {
        this.maths = maths;
    }

    public void mathClass() {
        System.out.println("Oare ce Maths am primit: " + maths.getClass());
        System.out.println(maths.sum(2, 4));
        System.out.println(maths.sum(1, 5));
        System.out.println(maths.product(2, 3));
    }
}

class Maths {
    public int sum(int a, int b) {
        return a + b;
    }

    public int product(int a, int b) {
//        return a * b;
        int sum = 0;
        for (int i = 0; i < a; i++) {
            sum = sum(sum, b);
        }
        return sum;
    }
}


// Key Points
// [2] Class Proxy using CGLIB (Enhancer) extending the proxied class
// [3] Spring Cache support [opt: redis]
// [4] Custom @Aspect, applied to methods in @Facade
// [6] Tips: self proxy, debugging, final
// [7] OPT: Manual proxying using BeanPostProcessor
