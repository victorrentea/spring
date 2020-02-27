package spring.training.tests;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class TestedApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestedApplication.class, args);
    }
}
@Service
@RequiredArgsConstructor
class A {
    private final B b;
    public boolean m(int i) {
        boolean ok = b.met();
        if (i == 0) {
            throw new IllegalStateException();
        }
        return ok;
    }
}

@Service
@RequiredArgsConstructor
class B {
    private final SMSSender c;
    public boolean met() {
        return c.sendSMS("mom()");
    }
}

@Service
@RequiredArgsConstructor
class SMSSender {
    public boolean sendSMS(String sms) {
        throw new IllegalArgumentException("Nu poti chema serv asta din teste");
    }
}
