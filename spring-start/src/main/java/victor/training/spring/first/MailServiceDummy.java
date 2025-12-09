package victor.training.spring.first;

// TODO use for local dev only
public class MailServiceDummy implements MailService {
    public void sendEmail(String subject) {
        System.out.println("MailServiceDummy: faking send of " + subject);
    }
}
