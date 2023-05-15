package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import victor.training.spring.sub.X;

import javax.annotation.PostConstruct;
import java.sql.SQLOutput;
import java.util.List;

@RequiredArgsConstructor
@Service
public class Y {
//  @Qualifier("mailServiceImpl")
//  private final MailService mailService; // polymorphic injection
//  @Lazy
//  private final X x;
//  private final MailServiceImpl mailService;
  private final MailService mailService;
  private final List<MailService> mailServiceToate;
  public int logic() {
    System.out.println("TOate de acel tip: " + mailServiceToate);
    mailService.sendEmail("I like 4 topics");
    return 1;
  }

  //@PostConstruct
  @EventListener(ApplicationStartedEvent.class)
  public void method() {
    System.out.println("ASTA");
  }
}
