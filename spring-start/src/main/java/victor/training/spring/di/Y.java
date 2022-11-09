package victor.training.spring.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import subp.X;

@Service
public class Y {
    private final Z z;

    public Y(Z z) {
        this.z = z;
    }

    public static void method() {
//        new Y(new Z(new Y()))
    }
    // #3 constructor injection (no @Autowired needed since Spring 4.3)
//    public Y(Z z) {
//        this.z = z;
//    }

//    @Autowired
//    public void method(X x) {
//        System.out.println("La startup: " + x);
//    }

    public int prod() {
        System.out.println(z);
        return 1 + z.prod();
    }
}
