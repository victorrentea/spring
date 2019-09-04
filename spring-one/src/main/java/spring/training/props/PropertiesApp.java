package spring.training.props;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Locale;

@SpringBootApplication
public class PropertiesApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(PropertiesApp.class);
    }

    @Bean
    public ManuallyConfigurable manuallyConfigurable() {
        return new ManuallyConfigurable("12345678");
    }

    @Override
    public void run(String... args) {
        System.out.println("Structured Props: ");
    }
}


// "Can't touch this!"
class ManuallyConfigurable {
    private final String secret;
    public ManuallyConfigurable(String secret) {
        this.secret = secret;
        System.out.println("Using secret: " + secret);
    }
    public void logic() {
        // using secret
    }
}


@Component
class TranslatingExceptionsPlay implements CommandLineRunner {
    @Autowired
    private MessageSource messageSource;

    @Override
    public void run(String... args) {
        Locale locale = new Locale("RO", "RO");
        try {
            throwingBizMethod(-1);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            // TODO implement it in a @RestControllerAdvice
        }
    }

    private void throwingBizMethod(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Dear user, i must be negative, but given " + i);
        }
    }
}

