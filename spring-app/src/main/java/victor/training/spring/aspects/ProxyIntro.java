package victor.training.spring.aspects;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

// de ce sa fii atent astea 45 min pana la masa ?
// @Transactional @Secured/@PreAuthorized, @Cacheable, @Timed, @Retryable, @Aspect
public class ProxyIntro {
    public static void main(String[] args) {
        // WE play the role of Spring here ...
        Maths maths = new MathsInterceptat();
        SecondGrade secondGrade = new SecondGrade(maths);
        // imagine un Apel HTTP aici
        secondGrade.mathClass();
    }
}
class MathsInterceptat  extends Maths{
    @Override
    public int sum(int a, int b) {
        System.out.println("Chema suma intre " + a + " cu " + b);
        return super.sum(a, b);
    }
}
// cerinta deasupra acestei linii scrie ceva asa incat sa pui in consola toate apelurile
// pe care SecondGrade le face catre Maths (param+ return value)
// ------------------------
class SecondGrade {
    private final Maths maths;
    SecondGrade(Maths maths) {
        this.maths = maths;
    }
    public void mathClass() {
        System.out.println("8 + 4 = " + maths.sum(8, 4));
        System.out.println("6 + 6 = " + maths.sum(6, 6));
        System.out.println("4 x 3 = " + maths.product(4, 3));
    }
}
class Maths {
    public int sum(int a, int b) {
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

