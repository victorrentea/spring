package victor.training.spring.transaction.exercises.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Disabled
@SpringBootTest
class ExceptionsExercisesTest {
   @Autowired
   Caller caller;
   @Autowired
   PlatformTransactionManager transactionManager;
   @Autowired
   ExceptionalEntityRepo repo;

   @BeforeEach
   final void before() {
      repo.deleteAll(); // explicit cleanup as tests cannot be @Transactional here
   }

   @Test
   void runtimeExceptions_markCurrentTransactionForRollback() {
      assertThatThrownBy(() -> caller.runtimeExceptions_markCurrentTransactionForRollback());

      assertThat(repo.findAll()).hasSize(0);
   }

   @Test
   void checkedExceptions_doNOTCauseRollback() {
      assertThatThrownBy(() -> caller.checkedExceptions_doNOTCauseRollback())
          .isNotInstanceOf(RuntimeException.class);

      assertThat(repo.findAll()).hasSize(1);
   }

   @Test
   void rollbackForChecked() {
      assertThatThrownBy(() -> caller.rollbackForChecked());

      assertThat(repo.findAll()).hasSize(0);
   }

   @Test
   void runtimeException_ifUnseenByProxy_doesntMarkForRollback() {
      try {
         caller.runtimeException_ifUnseenByProxy_doesntMarkForRollback();
      } catch (Exception e) {
         System.err.println("Ignored: " + e);
      }

      assertThat(repo.findAll()).hasSize(2);
   }

   @Test
   void separateTransaction_isRolledbackIndependently() {
      caller.separateTransaction_isRolledbackIndependently();

      assertThat(repo.findAll()).hasSize(2);
   }
}

