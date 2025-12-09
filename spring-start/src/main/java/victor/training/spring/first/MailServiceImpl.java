package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    //  private final MailSender sender; // TODO this bean is automatically defined by spring

    public void sendEmail(String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@victorrentea.ro");
        message.setTo("victor@victorrentea.ro");
        message.setSubject("Training Offer");
        message.setText(body);
        System.out.println("MailServiceImpl: sending email (pretend): " + message);
        //    sender.send(message);
    }
}
