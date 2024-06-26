package victor.training.spring.first;

// TODO for local dev only
public class MailServiceDummy implements MailService {
  public void sendEmail(String subject) {
    System.out.println("DUMMY SENDER: " + subject);
  }
}
