package victor.training.spring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

@SpringBootApplication //1 uncomment
public class ProxyIntro {
    // 4 comment:
//    public static void main(String[] args) {
//        // Play the role of Spring here ...
//        Maths maths = new Maths();
//        SecondGrade secondGrade = new SecondGrade(new Wrapper(maths));
//        new ProxyIntro().run(secondGrade);
//    }

    // #2 uncomment:
    public static void main(String[] args) {SpringApplication.run(ProxyIntro.class, args);}

    @Autowired // uncomment to run in Spring #3
    public void run(SecondGrade secondGrade) {
        System.out.println("Running Maths class...");
        secondGrade.mathClass();
    }
}
@Service
class SecondGrade {
    private final Maths maths;
    SecondGrade(Maths maths) { // spring nu va injecta clasa REALA Maths ci un wrapper peste. o clasa care extinde pe-a mea!!
        this.maths = maths;
    }
    public void mathClass() {
        System.out.println("2 + 4 = " + maths.sum(2, 4));
        System.out.println("1 + 5 = " + maths.sum(1, 5));
        System.out.println("2 x 3 = " + maths.product(2, 3));
    }
}

@Facade
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
