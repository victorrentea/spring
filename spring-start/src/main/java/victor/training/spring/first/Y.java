package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Y {
  private final MailService mailService; // polymorphic injection
  private final PersonService gigel;
//  @Value("${welcome.welcomeMessage}")
//  private final String message = "skip";
  private final WelcomeProps props;

// acum lombok genereaza EXACT ctor de mai jos
//  public Y(MailService mailService, PersonService gigel, @Value("${welcome.welcomeMessage}") String message) {
//    this.mailService = mailService;
//    this.gigel = gigel;
//    this.message = message;
//  }

//  @Autowired
//  private ApplicationContext applicationContext;

  public int logic() {
    props.setWelcomeMessage("OMG setter!!!! 🤢🤢🤢🤢🤢");
    mailService.sendEmail("I like 4 topics : " + props.getWelcomeMessage());
    // risk: s-a schimbat numele crapa
  // DI crapa la startup ❤️❤️❤️❤️❤️
    // crapa abia la callul metodei, potential la 2 zile dupa punerea in prod
//    var x = applicationContext.getBean("gigel");
//    var y = applicationContext.getBean("gigel",PersonService.class);
    gigel.method();
    return 1;
  }
}
