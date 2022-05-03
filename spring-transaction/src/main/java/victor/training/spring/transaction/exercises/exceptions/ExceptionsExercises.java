package victor.training.spring.transaction.exercises.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

// TODO fix tests
@SpringBootApplication
public class ExceptionsExercises {

   @Transactional
   public void runtimeExceptions_markCurrentTransactionForRollback() {
      // Someone calls this method within their transaction
      // TODO kill their transaction
   }
   @Transactional
   public void checkedExceptions_doNOTCauseRollback() throws Exception{
      // TODO prove that by default throwing any Checked exception (ex: some IOException)
      //  does not break the caller transaction
   }

   @Transactional
   public void rollbackForChecked() throws Exception{
      // TODO break the caller transaction on any checked exceptions. Hint: rollbackFor=
      throw new IOException();
   }

   public void runtimeException_ifUnseenByProxy_doesntMarkForRollback() {
      // TODO @see Caller class
      throw new RuntimeException();
   }

   @Transactional// (propagation=?)
   public void separateTransaction_isRolledbackIndependently() {
      // TODO avoid breaking the caller transaction via propagation=
      throw new RuntimeException();
   }
}


@Service
@RequiredArgsConstructor
class Caller {
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
      exercises.runtimeException_ifUnseenByProxy_doesntMarkForRollback();

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
