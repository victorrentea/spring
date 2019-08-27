package victor.training.spring.testing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class CodInfailibilApp {
    public static void main(String[] args) {
        SpringApplication.run(CodInfailibilApp.class, args);
    }



}

@Service
class Cuib {
    private final Gandac gandac1;
    Cuib(Gandac gandac1) {
        this.gandac1 = gandac1;
    }
    public void m() {
        gandac1.m();
    }
}
@Service
class Gandac {
    private final DoamneDoamne doamne;

    Gandac(DoamneDoamne doamne) {
        this.doamne = doamne;
    }
    public void m() {
        if (doamne.pray("Mine") < 0) {
            throw new IllegalArgumentException("Singur");
        } else {
            System.out.println("Feeling confident.");
        }
    }
}

@Service
class DoamneDoamne {
    public int pray(String prayer) {
//        return 1;
        throw new RuntimeException("Tre sa fie in prod ca sa mearga");
    }
}