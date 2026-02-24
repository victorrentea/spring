package victor.training.spring.first;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Primary
//@Profile("!prod") // PR rejected: don't expect operations to always remember to set the prod profile in production. Instead, hack away your local environment.
@Profile("local") // -Dspring.profiles.active=local
public class MailServiceDummy implements MailService {
  public void sendEmail(String subject) {
    System.out.println("MailServiceDummy: faking send of " + subject);
  }
}
