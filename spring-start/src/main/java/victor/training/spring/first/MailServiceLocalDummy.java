package victor.training.spring.first;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("local")
@Primary //daca-l vad, il iau
//@Primary // springule, ori de cate ori asta se bate cu alta pt injectie, alege-o p'asta
// TODO when starting the app locally,
//  don't send any emails, log then instead
public class MailServiceLocalDummy implements MailService {
  public void sendEmail(String subject) {
    System.out.println("DUMMY EMAIL SENDER sending an email with subject=" + subject);
  }
}
