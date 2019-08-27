package spring.training.props;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private StructuredProps props;

    @Value("${my.secret:12345678}")
    private String secret;

    @Bean
    public ManuallyConfigurable manuallyConfigurable(@Value("${my.secret:12345678}") String secret) {
        return new ManuallyConfigurable(secret);
    }

    @Override
    public void run(String... args) {
        System.out.println("Parola este: " + secret);
        System.out.println("Structured Props: " + props);
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
class TranslatingExceptionsPlay implements CommandLineRunner{
    @Autowired
    private MessageSource messageSource;

    @Override
    public void run(String... args) throws Exception {
        Locale locale = new Locale("RO", "RO");
        try {
            altaMetoda(-1);
        } catch (MyException e) {
            String key = e.getCode().code;
            String message = messageSource.getMessage(key, null, locale);
            System.out.println("Error: " + message);
        }
    }

    private void altaMetoda(int i) {
        if (i < 0) {
            throw new MyException(MyException.ErrorCode.I_NEGATIVE);
        }
    }
}

