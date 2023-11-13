package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class Y {
  @Autowired
  private MailService mailService; // polymorphic injection
  @Autowired
  private PersonService gigel;
  @Value("${welcome.welcomeMessage}")
  private String message;


  // (recommended) constructor injection => 😏 replace with @RequiredArgsConstructor
  public Y(MailService mailService) {
    this.mailService = mailService;
  }

  @Autowired
  private ApplicationContext applicationContext;

  public int logic() {
    mailService.sendEmail("I like 4 topics : " + message);
    // risk: s-a schimbat numele crapa
  // DI crapa la startup ❤️❤️❤️❤️❤️
    // crapa abia la callul metodei, potential la 2 zile dupa punerea in prod
//    var x = applicationContext.getBean("gigel");
//    var y = applicationContext.getBean("gigel",PersonService.class);
    gigel.method();
    return 1;
  }
}
