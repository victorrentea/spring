package victor.training.spring.life;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Profiles implements CommandLineRunner {
    @Qualifier("varza")
    @Autowired
    private I i;
//    public Profiles(@Qualifier("varza") I i) {
//        this.i = i;
//    }

@Autowired
    public void setI(@Qualifier("varza")  I i) {
        this.i = i;
    }

    public void run(String... args) throws Exception {
        i.m();
    }
}
interface I {
    void m();
}
@Component("varza")
class IA implements I {
    public void m() {
        System.out.println("A");
    }
}
@Component
class IB implements I {
    public void m() {
        System.out.println("B");
    }
}
