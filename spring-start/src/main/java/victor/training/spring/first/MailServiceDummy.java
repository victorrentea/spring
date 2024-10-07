package victor.training.spring.first;

import org.springframework.context.annotation.Profile;

@Profile("local")
public class MailServiceDummy
    implements MailService {

  public void sendEmail(String subject) {
    System.out.println("DUMMY SENDER: " + subject);
  }
}
