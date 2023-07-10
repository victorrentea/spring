package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import victor.training.spring.bean.X;

@RequiredArgsConstructor // pune pe ctor @Lazy si @Value
// de mai jos multumita lombok.config
@Service
public class Y {
  @Lazy // cheating
  private final X x;
  private final MailService mailService;
  @Value("${welcome.welcomeMessage}") // inject this from the configuration files
  private final String message;


  public int logic() {
    mailService.sendEmail("I like 4 topics : " + message);
    System.out.println("Parola: "+ pass);
    return 1;
  }
  @Value("${db.password}")
  private String pass;
}
