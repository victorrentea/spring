package com.example.aspects;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class InterfaceProxy {
    public static void main(String[] args) {
        MateImpl mateImpl = new MateImpl();
        InvocationHandler h = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("Cheama fraeru metoda " + method.getName() +
                        " cu param " + Arrays.toString(args));
                return method.invoke(mateImpl, args);
            }
        };
        Mate mate = (Mate) Proxy.newProxyInstance(InterfaceProxy.class.getClassLoader(),
                new Class<?>[]{Mate.class}, h);

        altaMetodaDeBiz(mate);
    }

    private static void altaMetodaDeBiz(Mate mate) {
        System.out.println("Oare cu cine vorbestc ? " + mate.getClass());
        System.out.println(mate.sum(1, 1));
        System.out.println(mate.sum(2,0));
        System.out.println(mate.sum(3,-1));
        System.out.println(mate.sum(3,1));
        System.out.println(mate.prod(2,1));
    }
}

interface Mate {
    int sum(int a, int b);
    int prod(int a, int b);
}
class MateImpl implements Mate {

    @Override
    public int sum(int a, int b) {
        return a + b;
    }

    @Override
    public int prod(int a, int b) {
        return a * b;
    }
}