package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

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
//      RestTemplate rest = new RestTemplate();
//      rest.getForObject() // blochezi 2 sec aii threadrul


      Message message = new Message("jpa");
      System.out.println("ID-u nou este inainte " + message.getId());
      repo.save(message);  // dupa .persist(), JPA e obligat sa atribuie ID acelei entitati noi.
      // daca iei PK din SEQUENCE --> vei vedea INSERT abia la finele @Tranzactiei
      // daca iei PK din IDENTITY ---> hibernate e OBLIGAT sa rulee INSERTUl AICI pe loc nu la final
      System.out.println("ID-u nou este " + message.getId());


      try {
         jdbc.update("INSERT INTO MESSAGE(ID, MESSAGE) VALUES ( -1,'ALO' )");
//         this.riskyStep();// does not cause a rollback since no RuntimeException
         // goes through any Transactional proxy
//         System.out.println(other.getClass());
//         other.riskyStep();
         other.persistError("Test");
      } catch (Exception e) {
      }
      System.out.println("Ies din metoda");
   }

   @Transactional
   public void riskyStep() throws IOException {
      throw new RuntimeException("");
   }

   //   @Transactional
   public void transactionTwo() {
      Message message = repo.findById(1L).get();

      message.setMessage("nou mesaj");
      System.out.println("Ies din met");
      repo.save(message);
      // TODO Repo API
      // TODO @NonNullApi
   }
}

@Service
@RequiredArgsConstructor
    // generates constructor for all final fields, used by Spring to inject dependencies
class AnotherClass {
   private final MessageRepo repo;


   // vii cu Tx deja deschisa la metoda asta --->
   // ce diferenta mai e daca pui @Transactioanl pe asta
   @Transactional// (rollbackFor = Exception.class)
   public void riskyStep() throws IOException {
      throw new IOException("");
   }

   @Transactional(propagation = Propagation.REQUIRES_NEW)
   public void persistError(String message) {
      repo.save(new Message("EROARE din tx2: " + message));
      throw new RuntimeException("Bum");
   }
}