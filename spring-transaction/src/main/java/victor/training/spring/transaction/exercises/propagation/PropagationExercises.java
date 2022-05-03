package victor.training.spring.transaction.exercises.propagation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class PropagationExercises {
   @Autowired
   private AService service;

   // TODO Determine for each flow: BEFORE WHAT METHOD DOES THE TRANSACTION START ?
   // Hint: use p6spy to check your answer
   // @EventListener(ApplicationStartedEvent.class) // uncomment
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

