package victor.training.spring.first;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Primary // daca ai de ales intre beanul asta si altul, asta castiga
@Profile("local") // acest bean sa fie DETECTAT DOAR daca profilul e activ
public class MailServiceLocalDummy implements MailService {
  public void sendEmail(String subject) {
    System.out.println("DUMMY EMAIL SENDER sending an email with subject=" + subject);
  }
}
