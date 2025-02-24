package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Primary
//@Profile("prod") // risky: may forget to enable in the HOLY PROD
@Profile("!local")// if I enable this profile this bean will be ignored
public class MailServiceImpl implements MailService {
  //  private final MailSender sender; // TODO this bean is automatically defined by spring

  public void sendEmail(String body) {
//    System.getProperty() // vm props -Dbla=val
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("noreply@victorrentea.ro");
    message.setTo("victor@victorrentea.ro");
    message.setSubject("Training Offer");
    message.setText(body);
    System.out.println("REAL EMAIL SENDER: sending email: " + message);
    //    sender.send(message);
  }
}
