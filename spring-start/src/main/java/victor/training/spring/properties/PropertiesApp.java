package victor.training.spring.properties;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableConfigurationProperties
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
