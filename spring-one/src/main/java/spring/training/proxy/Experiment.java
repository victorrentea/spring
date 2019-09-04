package spring.training.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class Experiment {
    public static void main(String[] args) {
        MateImpl realStuff = new MateImpl();

        InvocationHandler h = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("Cheama userul metoda "
                        + method.getName() + " cu param " + Arrays.toString(args));
                return method.invoke(realStuff, args);
            }
        };

        Mate mate = (Mate) Proxy.newProxyInstance(Experiment.class.getClassLoader(),
                new Class<?>[]{Mate.class},
                h);
        biznisLogic(mate);
    }

    private static void biznisLogic(Mate mate) {
        System.out.println("Oare cu cine vorbesc ? " + mate.getClass());
        System.out.println(mate.sum(1,1));
        System.out.println(mate.sum(0,2));
        System.out.println(mate.sum(-1,3));
        System.out.println(mate.sum(1,3));
        System.out.println(mate.product(2,3));
    }
}

interface Mate {
    Integer sum(int a, int b);
    Integer product(int a, int b);
}

class MateImpl implements Mate {
    @Override
    public Integer sum(int a, int b) {
        return a + b;
    }

    @Override
    public Integer product(int a, int b) {
        return a  *b;
    }
}