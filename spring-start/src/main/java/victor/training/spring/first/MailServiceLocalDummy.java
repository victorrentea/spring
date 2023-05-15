package victor.training.spring.first;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MailServiceLocalDummy implements MailService {
  public void sendEmail(String subject) {
    System.out.println("DUMMY EMAIL SENDER sending an email with subject=" + subject);
  }
}
