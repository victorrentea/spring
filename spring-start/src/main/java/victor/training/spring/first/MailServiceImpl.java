package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//@Profile("!local") // asa DA!

//@Profile("prod") // activez clasa asta doar pe productie?
    // nu si pe staging/load/acceptante,
// From YOLO Dev ideology (You Only Live Once, Carpe Diem,  Depuis moi, le deluge)
// I don't always test my code, but when I do, I do it in production
public class MailServiceImpl implements MailService {
  //  private final MailSender sender; // TODO uncomment and watch it failing because it requires properties to be auto-defined

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
