package victor.training.spring.props;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class PropertiesApp implements  CommandLineRunner{
    public static void main(String[] args) {
        SpringApplication.run(PropertiesApp.class);
    }

    @Autowired
    EmailSender emailSender;
    @Override
    public void run(String... args) throws Exception {
        emailSender.sendEmail();
    }
}

@Service
class EmailSender {
    @Value("${email.reply-to}") // nu faceti asta:
    private String replyTo;

    public void sendEmail() {
        //
        System.out.println("Add reply-to: " + replyTo);
    }
}


