package victor.training.spring.first;

import lombok.extern.slf4j.Slf4j;

// nu e bean pentru spring
@Slf4j
// TODO use solely on local dev env
public class MailServiceDummy implements MailService {
  public void sendEmail(String subject) {
    System.out.println("DUMMY SENDER: " + subject);
  }
}
