package victor.training.spring.transaction.challenge;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Slf4j
@Component
class PropagationExercises {
   @Autowired
   private AService service;

   // TODO uncomment and try to determine for each flow:
   // BEFORE WHAT METHOD DO NEW TRANSACTION START ?
   //   @EventListener(ApplicationStartedEvent.class)
   public void onStartupCallAll() {
      System.out.println("! This method does NOT have a transaction open !");
      System.out.println("================ FLOW #1 ==================");
      service.a1();
      System.out.println("================ FLOW #2 ==================");
      service.a2();
      System.out.println("================ FLOW #3 ==================");
      service.a3();
      System.out.println("================ FLOW #4 ==================");
      service.a4();
      System.out.println("================ FLOW #5 ==================");
      service.a5();
      System.out.println("================ FLOW #6 ==================");
      service.a6();
   }
}

