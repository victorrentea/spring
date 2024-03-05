package victor.training.spring.first.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;

//@EnableGlobalMethodSecurity(order = 5)
//@EnableCaching(order = 7)
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
