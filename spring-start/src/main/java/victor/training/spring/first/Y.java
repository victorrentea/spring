package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class Y {
  @Qualifier("mailServiceImpl")
  private final MailService mailService; // polymorphic injection
//  @Value("${welcome.welcomeMessage}") // inject this from the configuration files
  private final String message = "HALO";

  @Autowired
  //@Qualifier("gigel") <- nu mai e necesar, poti doar sa numesti punctul de inejctie cum trebuie
  private PersonService gigel;
  // DI crapa la startup ❤️❤️❤️❤️❤️

  // (recommended) constructor injection => 😏 replace with @RequiredArgsConstructor
  public Y(MailService mailService) {
    this.mailService = mailService;
  }

  @Autowired
  private ApplicationContext applicationContext;

  public int logic() {
    mailService.sendEmail("I like 4 topics : " + message);
    // risk: s-a schimbat numele crapa
    // crapa abia la callul metodei, potential la 2 zile dupa punerea in prod
//    var x = applicationContext.getBean("gigel");
//    var y = applicationContext.getBean("gigel",PersonService.class);
    gigel.method();
    return 1;
  }
}
