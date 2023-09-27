package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
//@Profile("!local")
@RequiredArgsConstructor
//@ConditionalOnClass // daca am o clasa in classpath > eg daca gasesc RabbitMQ in classpath, active niste componente care configuraea Rabbit
@ConditionalOnMissingBean(type = "victor.training.spring.first.MailService") // daca nu vede alt bean de tipul X
@ConditionalOnProperty(value = "use.email.sender",havingValue = "prod", matchIfMissing = true) // pe baza de prop
public class MailServiceImpl implements MailService {
  //  private final MailSender sender; // TODO uncomment and watch it failing because it requires properties to be auto-defined
//TODO uneori insa nu vreau nici sa configurez un bean din asta pe local pt
//  are nevoie de mult config sau face @PostConstruct
//  @Conditional....
  @Value("${email.server}")
  private String emailServer;

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
