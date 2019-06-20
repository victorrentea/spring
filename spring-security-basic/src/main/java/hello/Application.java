package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(Application.class, args);
    }

}

// light mode
@Component
class A {
    @Bean
    public B b() {
        System.out.println("Nou bean");
        return new B();
    }
    @Bean
    public C c1() {
        return new C(b());
    }
    @Bean
    public C c2() {
        return new C(b());
    }
}
class B {}
class C {
    private final B b;

    C(B b) {
        this.b = b;
    }
}
