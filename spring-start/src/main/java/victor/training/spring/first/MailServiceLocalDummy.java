package victor.training.spring.first;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
@ConditionalOnProperty(value = "mail.sender.dummy", havingValue = "true")
//@Profile("local")
@Primary
// TODO when starting the app locally, don't send any emails, log then instead
public class MailServiceLocalDummy implements MailService {
  public MailServiceLocalDummy() {
    System.out.println("Instantiated DUMMY");
  }
  @PostConstruct
  public void init() {
    System.out.println("init DUMMY");
  }
  public void sendEmail(String subject) {
    System.out.println("DUMMY EMAIL SENDER sending an email with subject=" + subject);
  }
}
