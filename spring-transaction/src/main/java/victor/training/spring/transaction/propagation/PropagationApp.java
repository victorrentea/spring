package victor.training.spring.transaction.propagation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PropagationApp {
    public static void main(String[] args) {
        SpringApplication.run(PropagationApp.class, args);
    }
}
