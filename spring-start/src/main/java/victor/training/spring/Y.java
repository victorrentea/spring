package victor.training.spring;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import victor.training.spring.first.MailService;

//@Service
public class Y {
//  @Autowired // injection point
//  private MailService mailService; // crapa, ca nu stie pecare

//  @Autowired
//  @Qualifier("mailServiceDummy") // numele beanului
//  private MailService mailService; // polymorphic injection

  @Autowired // numele punctului de injectie = camp decide beanul
  private MailService mailServiceDummy; // nu mai e necesar @Qualifier

  @Value("${props.gate}")
  private Integer gate;

  @PostConstruct
  public void laStartup() {
    System.out.println("mailService: " + mailServiceDummy);
  }


  public int logic() {
    mailServiceDummy.sendEmail("Go to gate " + gate);

    return 1;
  }
}

