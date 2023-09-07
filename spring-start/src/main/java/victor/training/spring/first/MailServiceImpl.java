package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
//@Profile("prod") // BAD PRACTICE: only to run in production env
//@Profile("!local") // garbage only for local env dev
@ConditionalOnMissingBean(MailServiceLocalDummy.class) // coupling to LOCAL env hacks
public class MailServiceImpl implements MailService {
  @Value("${prod.stuff.only}")
  private String cool;
  public MailServiceImpl() {
    System.out.println("Instantiated REAL");
  }
  //  private final MailSender sender; // TODO uncomment and watch it failing because it requires properties to be auto-defined
  @PostConstruct
  public void init() {
    System.out.println("init REAL");
  }
  public void sendEmail(String body) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("noreply@victorrentea.ro");
    message.setTo("victor@victorrentea.ro");
    message.setSubject("Training Offer");
    message.setText(body);
    System.out.println("REAL EMAIL SENDER sending email: " + message);
    //    sender.send(message);
  }
}
