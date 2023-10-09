package victor.training.spring.first.subp;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import victor.training.spring.first.MailService;

@Service
@RequiredArgsConstructor
public class Y {
  private final MailService mailService;
  private final String message = "HALO";

  public int logic() {
    mailService.sendEmail("I like 4 topics : " + message);
    return 1;
  }
}
