package victor.training.spring.props;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Locale;

@SpringBootApplication
public class PropertiesApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(PropertiesApp.class);
    }

    @Value("${my.secret:12345678}")
    private String secret;

    @Autowired
    private LdapProps ldapProps;

    @Bean
    public ManuallyConfigurable manuallyConfigurable(@Value("${my.secret:12345678}") String secret) {
        return new ManuallyConfigurable(secret);
    }

    @Override
    public void run(String... args) {
        System.out.println("Parola este: " + secret);
        System.out.println("Am citit: " + ldapProps);
    }
}


@Data
@Component
@ConfigurationProperties(prefix="ldap.props")
class LdapProps {
    private String url;
    private String user;
    private String password;
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
    public void run(String... args) throws Exception {
        Locale locale = new Locale("ES", "ES");
        try {
            altaMetoda(-1);
        } catch (MyException e) { // se va intampla intr-un @RestControllerAdvice
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

