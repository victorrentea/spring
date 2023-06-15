package victor.training.spring.first;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Component
@interface Mapper {

}

@Slf4j
// genereaza : private static final Logger log = LoggerFactory.getLogger(X.class);
@RequiredArgsConstructor

// TODO what other annotation register this class as a bean
//@Controller //  expun HTTP pe stilu vechi .jsp .jsf vaadin > genereaza HTML pe server
//@RestController // REST API
//@Service // business logic
//@Repository // DB, nu e necesar daca extends JpaRepository
//@Mapper // a mea, pot #sieu
//@Configuration // nu pt cod de application
//@MessageListener


@Component
public class X {
  private final Y y; // #2 field injection

  // #3 method injection (rarely used)
  // @Autowired public void init(Y y) {this.y2 = y;}

  public int logic() {
    return 1 + y.logic();
  }

//  @PostConstruct // problema nu poti sa folosesti @Transactional
//@EventListener(ApplicationStartingEvent.class)
  @EventListener
  public void helloSpring(ApplicationStartedEvent event) {
    System.out.println("Hello spring!");
    eventPublisher.publishEvent(new EventuMeu("wow! fain. eventuri. Observer patter. nu folosi ca-ti faci codu greu de urmarit"));
  }

  @Autowired
  private ApplicationEventPublisher eventPublisher;


  @EventListener // RISCANT
  public void method(EventuMeu eventuMeu) {
    System.out.println("Am primit " + eventuMeu);
  }
  @EventListener // RISCANT
  @Order(2)
  public void metho2d(EventuMeu eventuMeu) {
    System.out.println("Am primit2 " + eventuMeu);
  }




}
@Value
class EventuMeu {
   String date;
}