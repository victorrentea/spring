package victor.training.spring.first;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Slf4j
//@Service("mailservice2")

//@Profile("local") //2: @Profile("test") + you keep the calss in src/test/java
// TODO when starting the app locally, don't send any emails, log then instead

//@ConditionalOnProperty(name = "mail.sender", havingValue = "dummy")
//@ConditionalOnMissingBean(MailService.class)

//@Primary
public class MailServiceLocalDummy implements MailService {
  public void sendEmail(String subject) {
    System.out.println("DUMMY EMAIL SENDER sending an email with subject=" + subject);
  }
}


@Configuration
@ConditionalOnMissingBean(MailService.class)
class SomeConfig {
  @Bean
  MailServiceLocalDummy mailServiceLocalDummy() {
    return new MailServiceLocalDummy();
  }
}