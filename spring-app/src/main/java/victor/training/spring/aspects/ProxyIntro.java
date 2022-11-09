package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

//@SpringBootApplication
@Slf4j
public class ProxyIntro {
    public static void main(String[] args) {
        // pretend to BE Spring here
        Maths maths = new Maths();

        Callback handler = new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                System.out.println("Cheama fata metoda " + method.getName() + " cu param " + Arrays.toString(args));
                Object r = method.invoke(maths, args);
                System.out.println("I-a dat fetii " + r);
                return r;
            }
        };

        Maths proxy = (Maths) Enhancer.create(Maths.class, handler);

        SecondGrade secondGrade = new SecondGrade(proxy);
        new ProxyIntro().run(secondGrade);
    }

    //    public static void main(String[] args) {SpringApplication.run(ProxyIntro.class, args);}

//    @Autowired
    public void run(SecondGrade secondGrade) {
        System.out.println("At runtime...");
        secondGrade.mathClass();
    }
}

//-------- nu ai voie sa atingi nimic de mai jos -----
//@Service
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
class Maths {
    public int sum(int a, int b) {
        return a + b;
    }

    public int product(int a, int b) {
        return a * b;
    }
}


// Key Points
// [2] Class Proxy using CGLIB (Enhancer) extending the proxied class
// [3] Spring Cache support [opt: redis]
// [4] Custom @Aspect, applied to methods in @Facade
// [6] Tips: self proxy, debugging, final
// [7] OPT: Manual proxying using BeanPostProcessor
