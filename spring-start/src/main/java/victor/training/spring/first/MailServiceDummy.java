package victor.training.spring.first;

import org.springframework.stereotype.Service;

// TODO for local dev only
@Service//("mailServiceImpl") // nume custom
// numele default al acestui bean este "mailServiceDummy" (cu prima lower)
public class MailServiceDummy implements MailService {
  public void sendEmail(String subject) {
    System.out.println("DUMMY SENDER: " + subject);
  }
}
