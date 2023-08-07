package victor.training.spring.first.oups;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import victor.training.spring.first.MailService;

@Service("iwin")
//@Primary
public class MailServiceImpl implements MailService {
  @Override
  public void sendEmail(String subject) {

  }
}
