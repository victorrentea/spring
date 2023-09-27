package victor.training.spring.first.subp;

import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component // cu de toate, eg un mapper
//@Service // business logic: treburi de prin requirementuri
//@Repository // Manual DB access, nu e necesar daca extinzi o interfata din Spring Data
//@RequiredArgsConstructor
public class Alta implements CommandLineRunner {
//  @Autowired
////  @Lazy // de evitat
//  private PreDI preDI;

  // sunt intr-un obiect UNIC per app
  private String x; // nu tii date specifice UNUI request/mesaj in executie
  // --> te vei bate cu alte threaduri pe aceleasi date comune

  public void method(String currentUser) {
    x=currentUser; // NU!!
    // 100 ms
    System.out.println("INSERT INTO ... CREATED_BY="+x);
  }


  public void f() {
    System.out.println("Sa mearga");
  }

  @PostConstruct // nu poti insera in DB din el: pt ca ruleaza prea devreme in startup
  public void init() {
    System.out.println("verific un folder sa fie acolo");
  }

  @EventListener(ApplicationStartedEvent.class) // spring arunca o gramada de eventuri cand porneste
  public void candToataAppEGata() {
    System.out.println("Iau din DB ceva eg lista de tari");
    System.out.println("dau un mail: m-am nascut!");
    eventPublisher.publishEvent(new EventuMeu(1));
  }

  @Autowired
  private ApplicationEventPublisher eventPublisher;

  @Override
  public void run(String... args) throws Exception { // da acces la args de command line
    System.out.println("Pt batch joburi");
  }
}
@Value
class EventuMeu {
  int id;
}
@Component
class AltaClasaUndevaDeparte {
  @EventListener
  public void method(EventuMeu eventuMeu) {
    System.out.println("Am primit event printr-un event bus in memory al SPring " + eventuMeu.getId());
  }
  @EventListener
  public void sieu(EventuMeu eventuMeu) {
    System.out.println("#sieu");
  }
}
