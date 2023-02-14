package victor.training.spring.aspects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import java.util.List;

//@SpringBootApplication
public class ProxyIntro {
    public static void main(String[] args) {
        // pretend to BE Spring here
        Maths maths = new Maths();
        Decorator decorator = new Decorator(maths);
        SecondGrade secondGrade = new SecondGrade(decorator);
        new ProxyIntro().run(secondGrade);
    }
    //    public static void main(String[] args) {SpringApplication.run(ProxyIntro.class, args);}
//    @Autowired
    public void run(SecondGrade secondGrade) {
        System.out.println("At runtime...");
        secondGrade.mathClass();
    }
}

@Slf4j
class Decorator extends Maths {
    private final Maths maths;
    public Decorator(Maths maths) {
        this.maths = maths;
    }
    public int sum(int a, int b) {
        int r = maths.sum(a, b);
        log.info("calling sum with {} returned {}", List.of(a,b), r);
        return r;
    }
    public int product(int a, int b) {
        int r = maths.product(a, b);
        log.info("calling product with {} returned {}", List.of(a,b), r);
        return r;
    }

}

// =========a line. you can't touch anything under this line -=-=====
//@Service
@RequiredArgsConstructor
class SecondGrade {
    private final Maths maths;
    public void mathClass() {
        System.out.println(maths.sum(2, 4));
        System.out.println(maths.sum(1, 5));
        System.out.println(maths.product(2, 3));
    }
}

//@Facade
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
