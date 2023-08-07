package victor.training.spring.supb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import victor.training.spring.first.Adapter;
import victor.training.spring.first.Y;


//@Document
//class Article {
//}
//interface ArticleRepository extends MongoRepository {
//
//}

//@Component
//@Service = business logic that a non-tech could relate to
//@Repository // = DB access
//@Controller // = something to do .jsp/vaadin/.jsf
//@RestController // = REST API
//@MessageListener // MQ
@Adapter
public class X {
  private final Y y;
//  // constructor based injection ❤️
  public X(Y y) {
    this.y = y;
  }

//  @Autowired // method injection
//  public void init(Yyy y, Zzz z) {
//  }

//  @Autowired
//  ApplicationContext applicationContext;

  public int logic() {
    System.out.println("At runtime, 30 min later when ***that*** REST call happens");
    // risky: runtime failure if there is no Y bean defined in the app
//    Y bean = applicationContext.getBean(Y.class); // manual bean fetching

    return 1 + y.logic();
  }


  //  @PostConstruct //
  @EventListener
  public void stuff(ApplicationStartedEvent event) {
    System.out.println("At startup! " + event.getApplicationContext().getEnvironment().getProperty("mail.sender"));
    eventPublisher.publishEvent(new MyEvent(12));
  }
  @Autowired
  private ApplicationEventPublisher eventPublisher;
}

class MyEvent {
  private final int data;

  MyEvent(int data) {
    this.data = data;
  }

  public int getData() {
    return data;
  }
}

