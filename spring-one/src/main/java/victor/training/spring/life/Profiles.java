package victor.training.spring.life;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
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
@Component("varza")
class IA implements I { // hackareala pentru confort
    public void m() {
        System.out.println("A de pe disk ca e confortabil pentru dev");
    }
}
// only available without -Dspring.profiles.active=local
@Primary
@Profile("! local")
//@Profile("prod") // NICIODATA nu def un profil doar pt PROD.
@Component
class IB implements I { //asta e pe bune
    public void m() {
        System.out.println("B de pe alt FTP ca asa tre in prod");
    }
}
