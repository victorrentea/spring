package victor.training.spring.aspects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ProxySpringApp {
    public static void main(String[] args) {
        SpringApplication.run(ProxySpringApp.class, args);
    }

    @Autowired
    private SecondGrade secondGrade;

    @EventListener(ApplicationStartedEvent.class)
    public void run() {
        System.out.println("Running Maths class...");
        secondGrade.mathClass();
    }
}
