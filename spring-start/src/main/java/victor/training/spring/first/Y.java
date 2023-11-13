package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Y {
  @Autowired
  private MailService mailService; // polymorphic injection
//  @Value("${welcome.welcomeMessage}") // from the configuration files
  private String message = "HALO";
  // TODO @RequiredArgsConstructor with copyableAnnotations+=

  public int logic() {
    mailService.sendEmail("I like 4 topics : " + message);

    return 1;
  }
}
