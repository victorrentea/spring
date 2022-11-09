package victor.training.spring.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Y {
    @Autowired
    private  Z z;

    // #3 constructor injection (no @Autowired needed since Spring 4.3)
//    public Y(Z z) {
//        this.z = z;
//    }

    public int prod() {
        System.out.println(z);
        return 1 + z.prod();
    }
}
