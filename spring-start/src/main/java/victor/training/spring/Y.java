package victor.training.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import victor.training.spring.first.MailService;

//@Service
public class Y {
  @Autowired
  private MailService mailService; // polymorphic injection
  @Value("${props.gate}")
  private Integer gate;

  public int logic() {
    mailService.sendEmail("Go to gate " + gate);

    return 1;
  }
}
