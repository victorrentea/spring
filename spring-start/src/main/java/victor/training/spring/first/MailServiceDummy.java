package victor.training.spring.first;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

// pe local
@Service // ASTA💖
@Profile("dev") // creaza bean DOAR DACA profilele active includ "dev"
//@Primary // daca sunt mai multe beanuri de acelasi tip, asta e ales by default
public class MailServiceDummy implements MailService {
  public void sendEmail(String subject) {
    System.out.println("DUMMY SENDER: " + subject);
  }
}
