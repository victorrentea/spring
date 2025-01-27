package victor.training.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import victor.training.spring.first.MailService;

//@Service
public class Y {
//  @Autowired // injection point
//  private MailService mailService; // crapa, ca nu stie pecare

  @Autowired
  @Qualifier("mailServiceDummy") // numele beanului
  private MailService mailService; // polymorphic injection

  @Value("${props.gate}")
  private Integer gate;

  public Y() {// prea devreme, injectia inca nu s-a intamplat
    System.out.println("mailService: " + mailService);
  }


  public int logic() {
    mailService.sendEmail("Go to gate " + gate);

    return 1;
  }
}

