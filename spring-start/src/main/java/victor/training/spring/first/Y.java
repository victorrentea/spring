package victor.training.spring.first;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Slf4j
@Service
public class Y {

  @Autowired
  private WelcomeInfo welcomeInfo;

  @PostConstruct
  public void useTheConfig() {
    System.out.println("URLS: " + welcomeInfo.getSupportUrls());
//    welcomeInfo.setSupportUrls(Collections.emptyList());
  }

  @Qualifier("mailServiceImpl")
  private final MailService mailService; // polymorphic injection
  //  @Value("${welcome.welcomeMessage}") // inject this from the configuration files
  private final String message = "HALO";

  // (recommended) constructor injection => 😏 replace with @RequiredArgsConstructor
  public Y(MailService mailService) {
    this.mailService = mailService;
  }

  public int logic() {
    mailService.sendEmail("I like 4 topics : " + message);
    log.info("Sending ");
    eventPublisher.publishEvent(new MyOwnEvent(132));
    return 1;
  }

  @Autowired
  private ApplicationEventPublisher eventPublisher;

  @PostConstruct // immediately init THIS bean after it's configured
  public void post() {
    System.out.println("PostConstruct");
  }

  @EventListener(ApplicationStartedEvent.class) // after ALL beans have been configured
  public void appStarted() {
    System.out.println("App Started WITH PASS:" + pass);
  }
  @org.springframework.beans.factory.annotation.Value("${db.password}")
  String pass;
}

@Value // immutable
class MyOwnEvent {
  int value;
}