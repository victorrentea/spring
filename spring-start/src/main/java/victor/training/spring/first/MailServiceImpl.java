package victor.training.spring.first;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service("impl")
//@ConditionalOnProperty(
//    value = "email.active",
//    havingValue = "real",
//    matchIfMissing = true)
//@Profile("!local")
//@Primary // this wins against any other at an injection point
public class MailServiceImpl implements MailService {

//  @PostConstruct // when this bean is inited, after it's DInjected
  @EventListener(ApplicationReadyEvent.class)
  //or the other 7 alternatives for fine-grained control of startup pahse
  public void method() {
    System.out.println("connecting to Google SMTP server");
  }


  // TODO @Import this
//  @Profile("job")
  public static class IfThisAppWereAK8sOneTimeJob implements CommandLineRunner{
    @Override
    public void run(String... args) throws Exception {

    }
  }

  @PreDestroy // when spring shuts down
  public void notUsedUsually() {
    // clear some files
  }

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
