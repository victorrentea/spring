package victor.training.spring.first;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import victor.training.spring.first.pack.X;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

// - Dependency Injection: field, constructor, method
// - Defining beans: @Component & co, @ComponentScan
// - Cyclic dependencies
// - Startup code: PostConstruct, @EventListener, CommandLineRunner
// - Qualifier, bean names
// - Primary
// - Lombok tricks: @RAC, lombok.copyableAnnotations+=
// ----1h
// - Profile (bean & props)
// - @Autowired List<BeanInterface>
// - ApplicationContext#getBean
// - @Scope
// - @Configuration @Bean proxyMethods

// - @Value(${}) + @ConfigurationProperties
@SpringBootApplication
@EnableAsync
//@ComponentScan(basePackages = {
//    "victor.training.spring.first",
//    "another.pack"})

//@Import({
//    X.class
//}) // we want control what classes we define as bean

@Slf4j
public class FirstApplication implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(FirstApplication.class);
  }

  @Autowired
  private X x;

  @Override // from CommandLineRunner
  public void run(String... args) {
    log.info(" " + x.logic());
  }

//  @Transactional // this opens a tx on the current thread
  @EventListener(ApplicationReadyEvent.class)
  public void onAppStart() {
    log.info("App started OK 🎉");
    // those listnres will share the same tx with publisher
    eventPublisher.publishEvent(new MyEvent(87665));
  }
  @Autowired
  private ApplicationEventPublisher eventPublisher;
}
record MyEvent(int data) {} //my event
//- ------------- boundary

@Slf4j
@Component
class AnotherClassInADifferentModuleOfMyModulutih {
  @EventListener
//  @Order(200) // DONT DO THIS; listeners should not matter in what order they run
  @Async//("virtThrExe") // DONT makes the event run on a separate thread; no errors come back in publisher. publisher doesn't have to wait.
  // listeners may have to wait their turn in a waiting queue to get to run = volatile. Imagine a server crash
     /// ==> if you want "persistent @Async" => send yourself a durable message over a Q (eg rabbit)

  //@Bulkhead ()//restrict max 20 executions in parallel

  //  @TransactionalEventListener(AFTER_COMMIT) // runs after the COMMIT of the tx in which the event was published
  public void onSomethingInteresting(MyEvent event) {
    log.info("Runs via an event listener rabbit.send(), email.send('success..') " + event.data());

  }
}
@Slf4j
@Component
class MeTooo {
  @EventListener
//  @Order(100)
  public void onSomethingInteresting(MyEvent event) {
    log.info("Bis " + event.data());
  }
}
