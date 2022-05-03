package victor.training.spring.transaction.exercises.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class Caller {
   private final ExceptionsExercises exercises;
   private final ExceptionalEntityRepo repo;

   @Transactional
   public void runtimeExceptions_markCurrentTransactionForRollback() {
      repo.save(new ExceptionalEntity("before"));
      exercises.runtimeExceptions_markCurrentTransactionForRollback();
      repo.save(new ExceptionalEntity("after"));
   }

   @Transactional
   public void checkedExceptions_doNOTCauseRollback() throws Exception {
      repo.save(new ExceptionalEntity("before"));
      exercises.checkedExceptions_doNOTCauseRollback();
      repo.save(new ExceptionalEntity("after"));
   }

   @Transactional
   public void rollbackForChecked() throws Exception {
      repo.save(new ExceptionalEntity("before"));
      exercises.rollbackForChecked();
      repo.save(new ExceptionalEntity("after"));
   }

   @Transactional
   public void runtimeException_ifUnseenByProxy_doesntMarkForRollback() {
      repo.save(new ExceptionalEntity("before"));

      // TODO Stop the runtime exception from going through a Transactional Proxy!
      try {
         exercises.runtimeException_ifUnseenByProxy_doesntMarkForRollback();
      } catch (Exception e) {
         e.printStackTrace();
      }

      repo.save(new ExceptionalEntity("after"));
   }

   @Transactional
   public void separateTransaction_isRolledbackIndependently() {
      repo.save(new ExceptionalEntity("before"));
      try {
         exercises.separateTransaction_isRolledbackIndependently();
      } catch (Exception e) {
         System.out.println("Recovering from " + e);
      }
      repo.save(new ExceptionalEntity("after"));
   }

}
