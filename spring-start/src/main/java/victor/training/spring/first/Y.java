package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

//@Bean

//@RestController // REST stuff
//@Controller // se folosea in fremurile (apuse) ale .jsp, JSF, VAADIN, velocity, etc
//@Service // business logic
//@Component // techn stuff
//@Repository // DB stuff
public class Y {
  @Autowired
  private MailService mailService; // polymorphic injection
//  @Value("${props.gate}")
//  private Integer gate; // replace with injected Props
  @Autowired
  private Props props;

  public int logic() {
    mailService.sendEmail("Go to gate "
        + props.gate());

    return 1;
  }
}
