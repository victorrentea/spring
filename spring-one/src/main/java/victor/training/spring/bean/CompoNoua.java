package victor.training.spring.bean;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CompoNoua {
   private final OldClass old;
   private final EmailSender emailSender;

   @PostConstruct
   public void method() {
       emailSender.sendMail();
   }
}
interface EmailSender {

   void sendMail();
}

@Profile("!local")
@Component
class SMTPEmailSender implements EmailSender {
   @Override
   public void sendMail() {
      throw new IllegalArgumentException("Din localul tau da exceptie ca n-ai credentiale");
   }
}
@Profile("local")
@Component
class DummyEmailSender implements EmailSender {
   public void sendMail() {
      System.out.println("Ignoring email to send ");
   }
}