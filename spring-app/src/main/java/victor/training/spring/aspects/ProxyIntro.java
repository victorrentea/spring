package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

@Slf4j
public class ProxyIntro {
    public static void main(String[] args) {
        // WE play the role of Spring here ...
        Maths maths = new Maths() { // subclass
            @Override
            public int sum(int a, int b) {
                log.info("Calling sum(" + a + "," + b + ")");
                return super.sum(a, b);
            }
            @Override
            public int product(int a, int b) {
                log.info("Calling product(" + a + "," + b + ")");
                return super.product(a, b);
            }
        };
        SecondGrade secondGrade = new SecondGrade(maths);

        secondGrade.mathClass();
    }
}
// TODO write code above this line to intercept and log args of any call that SecondGrade does on Maths
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

