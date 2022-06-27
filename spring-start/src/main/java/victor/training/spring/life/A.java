package victor.training.spring.life;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class A {
    private final C c;
}
@Service
@RequiredArgsConstructor
class B {
    private final C c;

    @PostConstruct
    public void laStartup() {
        System.out.println("La start");
        new Thread(() -> method("john")).start();
        new Thread(() -> method("jane")).start();
    }

    @SneakyThrows
    public void method(String u) {
        c.setCurrentUsername(u);
        Thread.sleep(100);
        c.method();
    }
}

// inchipuie-ti ca esti intr-o app web
@Service
class C {
    private String currentUsername;

    public C() {
        System.out.println("Cate? 1. by default orice @ pe clasa face 1 instanta din acea clasa ");
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public void method() {
        System.out.println("Acu e logat userul " + currentUsername);
    }
}
