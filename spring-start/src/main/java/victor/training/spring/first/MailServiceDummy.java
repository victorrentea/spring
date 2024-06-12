package victor.training.spring.first;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Slf4j
@Service // doar pe localul meu
@Profile("local")
public class MailServiceDummy
    implements MailService {
  public void sendEmail(String subject) {
    System.out.println("DUMMY SENDER: " + subject);
  }
}
