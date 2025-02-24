package victor.training.spring.first;

// TODO use for local dev only
public class MailServiceDummy implements MailService {
  public MailServiceDummy(int i) {

  }

  public void sendEmail(String subject) {
    System.out.println("DUMMY SENDER: " + subject);
  }

  public void init() {

  }
}
