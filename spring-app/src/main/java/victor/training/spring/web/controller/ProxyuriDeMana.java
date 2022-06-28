package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ProxyuriDeMana {
    public static void main(String[] args) {
         // tu nu vei face asa ceva urmatorii 5 ani probabil
        // ma prefac c-as fi spring

        Mate realObject = new Mate(); // instanta 1 (reala)

        Callback h = new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                System.out.println("Sunt si eu aici si vad ca invoci metoda " + method.getName() + " cu arg " + Arrays.toString(args));
//                method.isAnnotationPresent(Cacheable.class)
                return method.invoke(realObject, args);
            }
        };
        Mate proxy = (Mate) Enhancer.create(Mate.class, h); // instanta 2 (proxy)
        Client client = new Client(proxy);
        client.clasa2();
    }
}


@RequiredArgsConstructor
class Client {
    private final Mate mate; // ceea ce ne da springu nu e defapt clasa reala a noasta ci un proxy.

    public void clasa2() {
        System.out.println("Tu cine esti ?!? Un Proxy" + mate.getClass());
        System.out.println(mate.suma(17, 23));
    }
}

class Mate {
    public int suma(int a, int b) {
        return a+ b;
    }
}