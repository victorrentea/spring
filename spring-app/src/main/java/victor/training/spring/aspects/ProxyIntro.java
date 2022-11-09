package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.Arrays;

@SpringBootApplication
@Slf4j
public class ProxyIntro {
//    public static void main(String[] args) {
        // pretend to BE Spring here
        //        Maths maths = new Maths();
        //
        //        Callback handler = new MethodInterceptor() {
        //            @Override
        //            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        //                if (method.isAnnotationPresent(Transactional.class)) {
        //                    System.out.println("E transactionala frate");
        //                }
        //                System.out.println("Cheama fata metoda " + method.getName() + " cu param " + Arrays.toString(args));
        //                Object r = method.invoke(maths, args);
        //                System.out.println("I-a dat fetii " + r);
        //                return r;
        //            }
        //        };
        //
        //        Maths proxy = (Maths) Enhancer.create(Maths.class, handler);
        //
        //        SecondGrade secondGrade = new SecondGrade(proxy);
        //        new ProxyIntro().run(secondGrade);
        //    }

    public static void main (String[]args){
        SpringApplication.run(ProxyIntro.class, args);
    }
    @Autowired
    public void run(SecondGrade secondGrade) {
        System.out.println("At runtime...");
        secondGrade.mathClass();
    }
}

//-------- nu ai voie sa atingi nimic de mai jos -----
@Service
class SecondGrade {
    private final Maths maths;

    SecondGrade(Maths maths) {
        this.maths = maths;
    }

    public void mathClass() {
        System.out.println("Puen mana pe Vasilica: " + maths.getClass());
        System.out.println(maths.sum(2, 4));
        System.out.println(maths.sum(1, 5));
        System.out.println(maths.product(2, 3));
    }
}

@Facade
/*final */class Maths { // <- nu porneste springu
    /*final*/ public int sum(int a, int b) { // <- silently ignored (BUG)?
        return a + b;
    }
    public int product(int a, int b) {
         new RuntimeException().printStackTrace();
//        return a * b;
        int produs = 0;
        for (int i = 0; i < a; i++) {
            produs = sum(produs, b); // APELUL LOCAL (in aceeasi clasa) NU POATE FI INTERCEPTAT de spring pentru ca
            // proxy-ul sta INAINTEA CLASEI
        }
        return produs;
    }
}


// Key Points
// [2] Class Proxy using CGLIB (Enhancer) extending the proxied class
// [3] Spring Cache support [opt: redis]
// [4] Custom @Aspect, applied to methods in @Facade
// [6] Tips: self proxy, debugging, final
// [7] OPT: Manual proxying using BeanPostProcessor
