package victor.training.spring.first;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

// TODO use for local dev only
@Service
@Order(30)
public class MailServiceDummy implements MailService {
  // @Retry din resilience4j, nu cu for { continue} ca-n liceu
  public void sendEmail(String subject) {
    System.out.println("MailServiceDummy: faking send of " + subject);
  }
}
