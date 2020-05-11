package victor.training.spring.life;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Profiles implements CommandLineRunner {
    @Autowired
    private IA i;
//    public Profiles(IA i) {
//        this.i = i;
//    }

@Autowired
    public void setI(  IA i) {
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
