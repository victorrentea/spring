package spring.training.proxy;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class Experiment {
    public static void main(String[] args) {
        MateImpl realStuff = new MateImpl();

        MethodInterceptor callback = new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                System.out.println("Cheama userul metoda "
                        + method.getName() + " cu param " + Arrays.toString(args));
                return method.invoke(realStuff, args);
            }
        };
        MateImpl mate = (MateImpl) Enhancer.create(MateImpl.class, callback);
        biznisLogic(mate);
    }

    private static void biznisLogic(MateImpl mate) {
        System.out.println("Oare cu cine vorbesc ? " + mate.getClass());
        System.out.println(mate.sum(1,1));
        System.out.println(mate.sum(0,2));
        System.out.println(mate.sum(-1,3));
        System.out.println(mate.sum(1,3));
        System.out.println(mate.product(2,3));
    }
}

class MateImpl {
    public Integer sum(int a, int b) {
        return a + b;
    }

    public Integer product(int a, int b) {
        return a  *b;
    }
}