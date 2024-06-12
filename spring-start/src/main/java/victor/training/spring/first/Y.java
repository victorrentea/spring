package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;

//@Bean

//@RestController // REST stuff
//@Controller // se folosea in fremurile (apuse) ale .jsp, JSF, VAADIN, velocity, etc
//@Service // business logic
//@Component // techn stuff
//@Repository // DB stuff
public class Y {
  @Autowired // injection point
//  @Qualifier("mailServiceImpl") //🤞 JDD
  private MailService mailServiceImpl; // polymorphic injection

//  @Value("${props.gate}")
//  private Integer gate; // replace with injected Props
  @Autowired
  private Props props;

  public int logic() {
    mailServiceImpl.sendEmail("Go to gate "
                              + props.gate());

    return 1;
  }
}
