package sub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import victor.training.spring.first.MailService;
import victor.training.spring.first.Props;

//toate astea il fac pe Spring sa def un bean din aceasta clasa
//@RestController - REST API
//@Service // business logic
//@Repository - DB access
//@Component - ceva ce nu se incadreaza in celelalte
//@Controller ------ (istoric) cand se genera HTML pe server: .jsp, .jsfx, .thymeleaf, .velocity, .freemarker
public class Y {
//  @Autowired
//  @Qualifier("mailServiceImpl") // specific ce nume de bean vreau
//  private MailService mailServiceImpl; // numele campului/param de ctor identifica beanul dorit dupa nume
  // polymorphic injection

  @Autowired
  private MailService mailService;

//  @Value("${props.gate}")
//  private Integer gate; // replace with injected Props

  @Autowired
  private Props props;

  public int logic() {
    props.setGate(-1); // NU AR TREBUI SA FIE POSIBIL

    mailService.sendEmail("Go to gate " + props.getGate());

    return 1;
  }
}
