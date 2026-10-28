package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public class ProxyIntro {
    public static void main(String[] args) {
        // WE play the role of Spring here ...
        Maths maths = new Maths();
        var proxy = new Wrapper(maths);
        SecondGrade secondGrade = new SecondGrade(proxy);
        secondGrade.mathClass();
    }
}

@Slf4j
class Wrapper extends Maths {
    private final Maths reala;
    Wrapper(Maths reala) {
        this.reala = reala;
    }
    @Override
    public int sum(int a, int b) {
        log.info("sum(" + a + "," + b + ")");
        return reala.sum(a, b);
    }

    @Override
    public int product(int a, int b) {
        return reala.product(a, b);
    }
}

// ------------------- LINE ------------------
@Service
class SecondGrade {
    private final Maths maths;
    SecondGrade(Maths maths) {
        this.maths = maths;
    }
    public void mathClass() {
        System.out.println("Oare ce mi-a injectat spring ? " + maths.getClass());
        System.out.println("8 + 4 = " + maths.sum(8, 4));
        System.out.println("6 + 6 = " + maths.sum(6, 6));
        System.out.println("4 x 3 = " + maths.product(4, 3));
    }
}

@Component
class Maths {
    @Logged
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

