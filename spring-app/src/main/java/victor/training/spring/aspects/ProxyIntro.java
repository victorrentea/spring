package victor.training.spring.aspects;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

public class ProxyIntro {
    public static void main(String[] args) {
        // WE play the role of Spring here ...
        Maths maths = new MathProxy(new Maths()); // a-la DECORATOR pattern
        SecondGrade secondGrade = new SecondGrade(maths);

        secondGrade.mathClass();
    }
}
class MathProxy extends Maths {
    private final Maths maths;
    MathProxy(Maths maths) {
        this.maths = maths;
    }

    @Override
    public int sum(int a, int b) {
        System.out.println("Calling sum with " + a + " and " + b);
        return maths.sum(a, b);
    }

    @Override
    public int product(int a, int b) {
        System.out.println("Calling product with " + a + " and " + b);
        return maths.product(a, b);
    }
}
// TODO aici
// scriind doar deasupra acestei linii, printeaza pe System.out orice apel
// la sum sau produs cu param dati
// ------------------------
@Service
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
@Service
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

