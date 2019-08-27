package victor.training.spring.proxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

@Slf4j
public class MagicInTheAir {

    public static void main(String[] args) {
        MathematicsJustImplem realStuff = new MathematicsJustImplem();

        MethodInterceptor callback = new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                log.debug("Calling {} with {}", method.getName(), Arrays.toString(args));
                return method.invoke(realStuff, args);
            }
        };
        MathematicsJustImplem golem = (MathematicsJustImplem)
                Enhancer.create(MathematicsJustImplem.class,
                callback);

        separateMethdo(golem);

    }

    private static void separateMethdo(MathematicsJustImplem golem) {
        System.out.println("How the heck that happens ? " + golem.getClass());
        System.out.println(golem.sum(1,1));
        System.out.println(golem.sum(0,2));
        System.out.println(golem.sum(1,2));
        System.out.println(golem.product(2,2));
    }
}


class MathematicsJustImplem {
    public Integer sum(int a, int b) {
        return a + b;
    }
    public Integer product(int a, int b) {
        return a * b;
    }
}
