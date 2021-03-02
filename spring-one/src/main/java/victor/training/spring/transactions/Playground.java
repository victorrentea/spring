package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class Playground {
   private final EntityManager entityManager;
   private final MessageRepo repo;
   private final JdbcTemplate jdbc;
   private final AnotherClass other;

   @Transactional
   public void transactionOne() throws IOException {
      repo.save(new Message("jpa"));
      try {
         jdbc.update("INSERT INTO MESSAGE(ID, MESSAGE) VALUES ( -1,'ALO' )");
//         this.riskyStep();// does not cause a rollback since no RuntimeException
         // goes through any Transactional proxy
//         System.out.println(other.getClass());
//         other.riskyStep();
      } catch (Exception e) {
         repo.save(new Message("EROARE: " + e.getMessage()));
      }
      System.out.println("Ies din metoda");
   }
   @Transactional
   public void riskyStep() throws IOException {
      throw new RuntimeException("");
   }
   @Transactional
   public void transactionTwo() {
      Message message = repo.findById(1L).get();

      message.setMessage("nou mesaj");
      System.out.println("Ies din met");
      // TODO Repo API
      // TODO @NonNullApi
   }
}

@Service
@RequiredArgsConstructor
@Transactional
    // generates constructor for all final fields, used by Spring to inject dependencies
class AnotherClass {
   private final MessageRepo repo;

   public void riskyStep() throws IOException {
      throw new RuntimeException("");
   }
}