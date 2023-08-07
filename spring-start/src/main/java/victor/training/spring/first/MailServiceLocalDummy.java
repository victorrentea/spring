package victor.training.spring.first;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "environment.name",
        havingValue = "local",
        matchIfMissing = false)
//@Profile("local")
@Primary
public class MailServiceLocalDummy implements MailService {
  public void sendEmail(String subject) {
    System.out.println("DUMMY EMAIL SENDER sending an email with subject=" + subject);
  }
}


@Profile("prod") // bad. prefer a 'local' profila
@Primary
@Component
class DarkCodeKinderSuprise {
  public void method() {
    System.out.println("# I don’t always test my code, " +
            "but when I do, I do it in production. YOLO");
  }
}