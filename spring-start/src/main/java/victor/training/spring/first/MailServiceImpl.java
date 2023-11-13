package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
//@Profile("!local") // asa DA!
@ConditionalOnMissingBean(MailServiceDummy.class)

//@Profile("prod") // activez clasa asta doar pe productie?
    // nu si pe staging/load/acceptante,
// From YOLO Dev ideology (You Only Live Once, Carpe Diem,  Depuis moi, le deluge)
// I don't always test my code, but when I do, I do it in production
public class MailServiceImpl implements MailService {

  @Value("${dinProp}")
  private String dinProp;

  @PostConstruct
  public void initBean() {
    System.out.println("La constructia beanului : " +dinProp);
  }

  @EventListener(ApplicationStartedEvent.class)
  public void initApp() {
    System.out.println("Later, after startup of the entire app");
  }

  //  private final MailSender sender; // TODO uncomment and watch it failing because it requires properties to be auto-defined

  public void sendEmail(String body) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("noreply@victorrentea.ro");
    message.setTo("victor@victorrentea.ro");
    message.setSubject("Training Offer");
    message.setText(body);
    System.out.println("REAL EMAIL SENDER: sending email: " + message + " din prop: " + dinProp );
    //    sender.send(message);
  }
}
