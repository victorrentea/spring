package victor.training.spring.life;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Profiles implements CommandLineRunner {
    private final I i;
    public Profiles(I i) {
        this.i = i;
    }
    public void run(String... args) throws Exception {
        i.m();
    }
}
interface I {
    void m();
}
@Component
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
