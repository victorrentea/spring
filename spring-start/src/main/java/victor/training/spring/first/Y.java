package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service

public class Y {
  private final MailService mailService; // polymorphic injection
  private final PersonService gigel;
  private final String message;

  public Y(MailService mailService, PersonService gigel, @Value("${welcome.welcomeMessage}") String message) {
    this.mailService = mailService;
    this.gigel = gigel;
    this.message = message;
  }

//  @Autowired
//  private ApplicationContext applicationContext;

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
