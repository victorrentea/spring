package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

@Slf4j
public class InterfaceProxies {
    public static void main(String[] args) {
        Mate mateImpl = new Mate();
        InvocationHandler h = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("Invoc metoda " + method.getName() + " cu param " + Arrays.toString(args));
                return method.invoke(mateImpl, args);
            }
        };
        IMate mate = (IMate) Proxy.newProxyInstance(InterfaceProxies.class.getClassLoader(),
                new Class<?>[]{IMate.class}, h); // JDK Proxies

        altaMetoda(mate);
    }

    private static void altaMetoda(IMate mate) {
        System.out.println("Oare cu cine vorbesc ? "  +mate.getClass());
        System.out.println(mate.suma(1, 1));
        System.out.println(mate.suma(2, 0));
        System.out.println(mate.suma(4, -2));
        System.out.println(mate.suma(4, 2));
        System.out.println(mate.produs(1, 2));
    }
}
interface IMate {
    int suma(int a, int b);
    int produs(int a, int b);
}
class Mate implements IMate {
    @Override
    public int suma(int a, int b) {
        return a+ b;
    }

    @Override
    public int produs(int a, int b) {
        return a * b;
    }
}