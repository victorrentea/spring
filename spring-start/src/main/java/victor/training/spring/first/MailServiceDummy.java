package victor.training.spring.first;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

// TODO for local dev only
@Service
@Profile("local")
@Primary // downside e ca Impl inca se configureaza ca bean, si ar putea crapa daca nu ai niceste props de ex
//("mailServiceImpl") // nume custom
// numele default al acestui bean este "mailServiceDummy" (cu prima lower)
public class MailServiceDummy implements MailService {
  public void sendEmail(String subject) {
    System.out.println("DUMMY SENDER: " + subject);
  }
}
