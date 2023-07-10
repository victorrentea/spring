package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import victor.training.spring.bean.X;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class Y {
  @Autowired
  @Lazy // cheating
  private X x;


  @Autowired
//  @Qualifier("mailServiceLocalDummy") // numesc beanul dorit
  private MailService mailService; // polymorphic injection

  //  @Value("${welcome.welcomeMessage}") // inject this from the configuration files
  private final String message = "HALO";

  public int logic() {
    mailService.sendEmail("I like 4 topics : " + message);

    return 1;
  }
}
