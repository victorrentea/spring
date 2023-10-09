package victor.training.spring.aspects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

//@EnableGlobalMethodSecurity(order = 3)
//@EnableCaching(order = 5)
@SpringBootApplication
public class ProxySpringApp {
    public static void main(String[] args) {
        SpringApplication.run(ProxySpringApp.class, args);
    }
    @Autowired
    public void run(SecondGrade secondGrade) {
        System.out.println("Running Maths class...");
        secondGrade.mathClass();
    }
}
