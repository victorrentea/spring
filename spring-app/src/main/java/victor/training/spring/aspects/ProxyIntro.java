package victor.training.spring.aspects;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

public class ProxyIntro {
    public static void main(String[] args) {
        // WE play the role of Spring here ...
        Maths maths = new Maths() {
            @Override
            public int sum(int a, int b) {
                int result = super.sum(a, b); // real call
//                System.out.println("sum \{a} + \{b} = \{result}"); // java24
                System.out.println("SRI: sum "+ a+ " + " + b +" = "+result); // java21
                return result;
            }
        };
        SecondGrade secondGrade = new SecondGrade(maths);
        // TODO logeaza param si rezultatele tutoro metodelor din Maths chemate de SecondGrade
        // -- pana aici ar face spring in startup
        // la runtime
        secondGrade.mathClass();
    }
}
// ------------------------  n-ai voie sa scrii nimic sub linia asta: fii-mea nu tre sa afle --------
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

