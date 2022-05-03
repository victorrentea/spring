package victor.training.spring.transaction.exercises.exceptions;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@SpringBootApplication
public class ExceptionsExercises {

   @Transactional
   public void runtimeExceptions_markCurrentTransactionForRollback() {
      // Someone calls this method within their transaction
      // TODO kill their transaction
      throw new RuntimeException();
   }
   @Transactional
   public void checkedExceptions_doNOTCauseRollback() throws Exception{
      // TODO prove that by default throwing any Checked exception (ex: some IOException)
      //  does not break the caller transaction
      throw new IOException();
   }

   @Transactional(rollbackFor = Exception.class)
   public void rollbackForChecked() throws Exception{
      // TODO break the caller transaction on any checked exceptions. Hint: rollbackFor=
      throw new IOException();
   }

   public void runtimeException_ifUnseenByProxy_doesntMarkForRollback() {
      // TODO @see Caller class
      throw new RuntimeException();
   }

   @Transactional(propagation = Propagation.REQUIRES_NEW)// (propagation=?)
   public void separateTransaction_isRolledbackIndependently() {
      // TODO avoid breaking the caller transaction via propagation=
      throw new RuntimeException();
   }
}