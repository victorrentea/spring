package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;


//@Profile("prod") // BAAAAD PRACTICE: this code will ONLY run when you activate 'prod' profile

@Service
//@Profile("!local") // pollution
@RequiredArgsConstructor
@ConditionalOnProperty(name = "some.prop")
//@ConditionalOnMissingBean( MailServiceLocalDummy.class)
public class MailServiceImpl implements MailService {
  private final MailSender sender; // TODO uncomment and watch it failing because it requires properties to be auto-defined
  @Value("${some.prop:hiddendefault value that only I know about}")
  private String prop;

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
