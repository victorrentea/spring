package com.example.aspects;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class ClassProxy {
    public static void main(String[] args) {
        Mate mateImpl = new Mate();
//        InvocationHandler h = new InvocationHandler() {
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//
//            }
//        };
//        Mate mate = (Mate) Proxy.newProxyInstance(ClassProxy.class.getClassLoader(),
//                new Class<?>[]{Mate.class}, h);

        Callback callback = new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                System.out.println("Cheama fraeru metoda " + method.getName() +
                        " cu param " + Arrays.toString(args));
                return method.invoke(mateImpl, args);
            }
        };
        // echivalentul conceptual a ce face CGLIB la runtime
//        mate = new Mate() {
//            @Override
//            public int sum(int a, int b) {
//                //chestii
//                return mateImpl.sum(a, b);
//            }
//        }
        Mate mate = (Mate) Enhancer.create(Mate.class, callback);

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

class Mate {
    public int sum(int a, int b) {
        return a + b;
    }
    public int prod(int a, int b) {
        return a * b;
    }
}