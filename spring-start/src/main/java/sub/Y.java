package sub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import victor.training.spring.first.MailService;

//toate astea il fac pe Spring sa def un bean din aceasta clasa
//@RestController - REST API
@Service // business logic
//@Repository - DB access

//@Component - ceva ce nu se incadreaza in celelalte

//@Controller ------ (istoric) cand se genera HTML pe server: .jsp, .jsfx, .thymeleaf, .velocity, .freemarker
public class Y {
  @Autowired
  private MailService mailService; // polymorphic injection
  @Value("${props.gate}")
  private Integer gate; // replace with injected Props

  public int logic() {
    mailService.sendEmail("Go to gate " + gate);

    return 1;
  }
}
