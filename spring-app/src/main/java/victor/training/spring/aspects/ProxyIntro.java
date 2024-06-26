package victor.training.spring.aspects;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

public class ProxyIntro {
    public static void main(String[] args) {
        Maths maths = new MathsIntercept();
        SecondGrade secondGrade = new SecondGrade(maths);
        secondGrade.mathClass();
    }
}
// fara a modifica nimic sub linia asta, printati fiecare
// apel de functie sum/produs cu parametrii ei
// pe care-l face SecondGrade (sa interceptam apelul)
class MathsIntercept extends Maths {
    public int sum(int a, int b) {
        System.out.println("Intercepted sum(" + a + ", " + b + ")");
        return super.sum(a, b);
    }
    public int product(int a, int b) {
        System.out.println("Intercepted product(" + a + ", " + b + ")");
        return super.product(a, b);
    }
}
// -----------------------------------------
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

