package victor.training.spring.first;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;

//@Import-ed -injected
//@Primary
//@Profile("prod") // spring only sees this bean if the active profile is "prod",
// eg  -Dspring.profiles.active=prod at startup
public class MailServiceImpl implements MailService {
  //  private final MailSender sender; // TODO this bean is automatically defined by spring

  public void sendEmail(String body) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("noreply@victorrentea.ro");
    message.setTo("victor@victorrentea.ro");
    message.setSubject("Training Offer");
    message.setText(body);
    System.out.println("REAL EMAIL SENDER: sending email: " + message);
    //    sender.send(message);
  }
}
