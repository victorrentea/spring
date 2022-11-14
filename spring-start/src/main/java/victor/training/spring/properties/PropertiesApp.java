package victor.training.spring.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class PropertiesApp {
    public static void main(String[] args) {
        SpringApplication.run(PropertiesApp.class, args);
    }

    @Value("${prop}")
    private String prop;

    @EventListener(ApplicationStartedEvent.class)
    public void method() {
        System.out.println("Prop from a file: " + prop);
    }
}
