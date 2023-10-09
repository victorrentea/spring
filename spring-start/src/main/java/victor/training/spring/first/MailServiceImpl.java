package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//@Profile("prod") // NU pun cod netestat vreodata in prod direct.
//@Profile("!local")
@ConditionalOnMissingBean(type = "victor.training.spring.first.MailServiceLocalDummy")
public class MailServiceImpl
    implements MailService {
  @Value("${from.email}")
  private String fromEmail;
  //  private final MailSender sender; // TODO uncomment and watch it failing because it requires properties to be auto-defined




  public void sendEmail(String body) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(fromEmail);
    message.setTo("victor@victorrentea.ro");
    message.setSubject("Training Offer");
    message.setText(body);
    System.out.println("REAL EMAIL SENDER sending email: " + message);
    //    sender.send(message);
  }
}
