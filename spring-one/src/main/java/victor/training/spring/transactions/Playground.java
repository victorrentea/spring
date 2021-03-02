package victor.training.spring.transactions;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

         // programatic tx management cu spring.
//         txTemplate.execute (status -> {
//            repo.save(new Message("EROARE din tx2: " + message));
//            return null;
//         })
      } catch (Exception e) {
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
      other.dinAceeasiTranzactieDarMaiAdanc();
      System.out.println("Ies din met");
      // TODO @NonNullApi
   }
}

@Data
class CleanupAfterTransactionEvent {
   private final List<File> temporaryFilesToDelete;
   private final List<String> emailRecipientsToNotify;
}

@Slf4j
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

   @SneakyThrows
   public void dinAceeasiTranzactieDarMaiAdanc() {
      File tempFile = Files.createTempFile("a",".tmp").toFile();

      publisher.publishEvent(new CleanupAfterTransactionEvent(Arrays.asList(tempFile), Collections.singletonList("vrentea@ibm.com")));
      log.debug("Inchid metoda");
   }

   @EventListener
//   @Transactional(propagation = Propagation.REQUIRES_NEW)
   public void imediatDupaAruncare(CleanupAfterTransactionEvent event) {
      log.debug("Imediat acum");
   }
   @EventListener
//   @Async
   public void imediatDupaAruncare2(CleanupAfterTransactionEvent event) {
      log.debug("Imediat acum2");
   }

   @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
   public void cleanup(CleanupAfterTransactionEvent event) {
      log.debug("sending emails: " + event.getEmailRecipientsToNotify());
   }
   @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
   public void cleanupFiles(CleanupAfterTransactionEvent event) {
      log.debug("Cleaning files: " + event.getTemporaryFilesToDelete());
      log.debug(repo.findAll().toString());
   }

   @Autowired
   ApplicationEventPublisher publisher;


}