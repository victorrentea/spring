package spring.training.props;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Locale;

@SpringBootApplication
public class PropertiesApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(PropertiesApp.class);
    }


    @Bean
    public ManuallyConfigurable manuallyConfigurable(@Value("${secret.mare}") String secret) {
        return new ManuallyConfigurable(secret);
    }

    @Override
    public void run(String... args) {
        System.out.println("Structured Props: ");
    }
}
@Service
class A {
    @Autowired
    ManuallyConfigurable configurable;
    @Value("${alt.secret.mare:12345678}")
    String secret;
    @PostConstruct
    public void printPasswordLoudAndClear() {
        System.out.println("PASS: " + secret);
    }
}


// "Can't touch this!"
class ManuallyConfigurable {
    private final String secretPwd;
    public ManuallyConfigurable(String secretPwd) {
        this.secretPwd = secretPwd;
        System.out.println("Using secret: " + secretPwd);
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
        Locale locale = new Locale("ES", "ES");
        try {
            throwingBizMethod(-1);
        } catch (IllegalArgumentException e) {
            //traduc cheia in mesaj
            String message = messageSource.getMessage(e.getMessage(), null, locale);
            System.err.println(message);
        }
    }

    private void throwingBizMethod(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("error.i.negative");
        }
    }
}

