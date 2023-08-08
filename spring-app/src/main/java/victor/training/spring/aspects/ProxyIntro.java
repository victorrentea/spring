package victor.training.spring.aspects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

public class ProxyIntro {
    public static void main(String[] args) {
        // WE play the role of Spring here ...
        Maths maths = new SubClassOfMaths();
        SecondGrade secondGrade = new SecondGrade(maths);

        secondGrade.mathClass();
    }
}
class SubClassOfMaths extends Maths {
    @Override
    public int sum(int a, int b) {
        int r = super.sum(a, b);
        System.out.println("Sum (" + a + ", " + b + ")=" + r);
        return r;
    }

    @Override
    public int product(int a, int b) {
        int r = super.product(a, b);
        System.out.println("Product (" + a + ", " + b + ")=" + r);
        return r;
    }
}
// TODO without my daughter finding out (ie writing code only above the line)
//  make sure you keep track of all the sum and product operations she did  (log them)
// ---------------------------
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

