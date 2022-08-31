package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ProxyIntro {
    public static void main(String[] args) {
        // pretend to BE Spring here
        Maths maths = new Maths() {
            @Override
            public int sum(int a, int b) {
                log.info("sum of " + a + " + " + b);
                return super.sum(a, b);
            }

            @Override
            public int product(int a, int b) {
                log.info("product of " + a + " + " + b);
                return super.product(a, b);
            }
        };
        SecondGrade secondGrade = new SecondGrade(maths);

        // TODO maniac father wants to log all the math operations done during school
        secondGrade.mathClass();
    }

}

class SecondGrade {
    private final Maths maths;

    SecondGrade(Maths maths) {
        this.maths = maths;
    }

    public void mathClass() {
        System.out.println(maths.sum(2, 4));
        System.out.println(maths.sum(1, 5));
        System.out.println(maths.product(2, 3));
    }
}

@Slf4j
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
