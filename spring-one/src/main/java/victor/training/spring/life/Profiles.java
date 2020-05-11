package victor.training.spring.life;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Profiles implements CommandLineRunner {
    private final List<I> toate;
    private final I safe;


    public Profiles(List<I> toate, I safe) {
        this.toate = toate;
        this.safe = safe;
    }

    public void run(String... args) throws Exception {
        toate.forEach(I::m);
        System.out.println("----");
        safe.m();
    }
}
interface I {
    void m();
}
@Component("varza")
class IA implements I { // hackareala pentru confort
    public void m() {
        System.out.println("A de pe disk ca e confortabil pentru dev");
    }
}
// only available without -Dspring.profiles.active=local
//@Primary
//@Profile("! local")
//@Profile("prod") // NICIODATA nu def un profil doar pt PROD.
@Component("safe")
class IB implements I { //asta e pe bune
    public void m() {
        System.out.println("B de pe alt FTP ca asa tre in prod");
    }
}
