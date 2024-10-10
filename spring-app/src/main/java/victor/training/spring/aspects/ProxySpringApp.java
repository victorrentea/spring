package victor.training.spring.aspects;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

//@EnableGlobalMethodSecurity(order = 1)
//@EnableCaching(order=2)
@SpringBootApplication
@Import({SecondGrade.class, Maths.class})
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

    @Order(1)
    @Bean // enables @Timed
    public TimedAspect timedAspect(MeterRegistry meterRegistry) {
        TimedAspect timedAspect = new TimedAspect(meterRegistry);
        return timedAspect;
    }
}
//AI Prompts:
//1. I have a Spring Boot 3 application. Write an aspect that intercepts all method calls to any class in package "victor.training.spring.aspects" and log the method name and parameters. Assume I have all the necessary dependencies in the pom.xml.
//2. Change the aspect to target only method annotated with a custom annotation
//3. If I annotate the class, all methods should be intercepted.
//4. Write another aspect to measure the execution time of these methods.
// sample: https://chatgpt.com/share/3cdeb17b-ce60-4c11-a9b1-76af8cd929e1