package victor.training.spring.first;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
//@DependsOn("anotherClass2") // when the other bean should be created before me
// for reasons that Spring cannot suspect
public class AnotherClass1 {
//  @Order(2) // BAD to do this as it becomes a
  // GLOBAL coupling point between all the listeners

  // BAD PRACTICE: the listeners should NOT matter in what order they run.
  // Alternative: chain a second event after the first
//  public void onMyEvent(SEcondEvent event) {

//  @Async // separate thread. AVOID unless you really need
  @EventListener
  public void onMyEvent(MyOwnEvent event) {
   log.info("Received " + event); // default,
    // on the same thread,
    // in the same transaction
    // if ex is thrown, the ex pops up in sender
    //  and blocks other next listeners
  }
}
@Slf4j
@Service
class AnotherClass2 {
//  @Order(1)
  @EventListener // at startup spring detects such @EventListener annot
  // and automatically registeres the listener in a
  // in mem message bus
  public void onMyEvent(MyOwnEvent event) {
    log.info("Received2 " + event); // def
//    publisher.sendEvent(new SEcondEvent()); //
  }
}

// DO NOT use events instead of method calls on a regular basis.
// it will make your code unmaintainable
// only use events in your logic if you plan to DECOUPLE MODULES
// (Modulith+ArchUnit or Microservices)
