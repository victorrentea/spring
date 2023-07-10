package victor.training.spring.aspects;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

// de ce sa fii atent astea 45 min pana la masa ?
// @Transactional @Secured/@PreAuthorized, @Cacheable, @Timed, @Retryable, @Aspect
public class ProxyIntro {
    public static void main(String[] args) {
        // WE play the role of Spring here ...
        Maths realBean = new Maths();

        Callback callback = new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] arguments, MethodProxy methodProxy) throws Throwable {
                System.out.println("Chem metoda '" + method.getName() + "' cu param " + Arrays.toString(arguments));
                return method.invoke(realBean, arguments);
            }
        };
        Maths proxy = (Maths) Enhancer.create(Maths.class, callback);
        SecondGrade secondGrade = new SecondGrade(proxy);
        // imagine un Apel HTTP aici
        secondGrade.mathClass();
    }
}
//class MathsInterceptat  extends Maths { // PROXY
//    private final Maths realBean;
//    MathsInterceptat(Maths real) {
//        this.realBean = real;
//    }
//
//    @Override
//    public int sum(int a, int b) {
//        System.out.println("Chema suma intre " + a + " cu " + b);
//        return realBean.sum(a, b);
//    }
//
//    @Override
//    public int product(int a, int b) {
//        System.out.println("Chema produs intre " + a + " cu " + b);
//        return realBean.product(a, b);
//    }
//}

// cerinta deasupra acestei linii scrie ceva asa incat sa pui in consola toate apelurile
// pe care SecondGrade le face catre Maths (param+ return value)
// ------------------------
class SecondGrade {
    private final Maths maths;
    SecondGrade(Maths maths) {
        this.maths = maths;
    }
    public void mathClass() {
        //cand eu chem o metoda pe 'maths', mai ruleaza cineva pana intru in acea metoda
        System.out.println("Inainte de apel. TZEAPA. obiectul pe caer Spring ti l-a " +
                           "injectat  NU E CEEA CE CREZI: "+maths.getClass());
        int rezultat = maths.sum(8, 4);
        System.out.println("8 + 4 = " + rezultat);
        System.out.println("6 + 6 = " + maths.sum(6, 6));
        System.out.println("4 x 3 = " + maths.product(4, 3));
    }
}
class Maths {
    public int sum(int a, int b) {
        System.out.println("Am ajuns in metoda reala");
        return a + b;
    }
    public int product(int a, int b) {
        return a * b;
    }
}


// Key Points
// - Class Proxy using CGLIB Enhancer to extend the proxied class
// - Proxy limitations: final methods/classes, local calls
// - Debug a Proxy
// - Custom @Aspect

