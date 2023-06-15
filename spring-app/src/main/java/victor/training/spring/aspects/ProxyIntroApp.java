package victor.training.spring.aspects;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

@EnableCaching
@SpringBootApplication
public class ProxyIntroApp {
    public static void main(String[] args) {
        SpringApplication.run(ProxyIntroApp.class, args);
    }
    @Autowired
    public void oricum(SecondGrade secondGrade) {
        System.out.println("Running Maths class...");
        secondGrade.mathClass();
    }

    @Bean
    public TimedAspect timedAspect(MeterRegistry meterRegistry) {
        return new TimedAspect(meterRegistry);
    }
}
