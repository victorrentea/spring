package victor.training.spring.transaction.playground;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class PlayJpa {
  private final MessageRepo repo;
  private final EntityManager entityManager;

  @Transactional(isolation = Isolation.READ_UNCOMMITTED) // intr-o tx daca JPA ti-a dat un @Entity, cand o iei din nou dupa ID nu mai face SELECT, ti-o dat in cache
  public void writeBehind() {
    var id = repo.save(new Message("ONE")).getId();
    log.info("Dupa save");
    // #1 @Entity e in memoria Java
    log.info("Gasesc?: " + repo.findById(id)); // nu face SELECT IN DB pt ca il iei dupa ID = 1st-level JPA caching
    log.info("Gasesc?: " + repo.findByMessage("ONE")); // Face INSERT ca urmeaza un SELECT IN DB
    log.info("Gasesc?: " + repo.findByMessage("nullu nu face bine la om")/*.orElseThrow().getMessage()*/);
    // #2 @Entity e INSERT in DB (dar nu COMMITuit inca)
//    if (true) throw new RuntimeException("INTENTIONAL");
    log.info("--- End of method");
  }
  // #3 @Entity e INSERT in DB (si COMMITuit)

  @Transactional // dirty check la final => UPDATE
  public void autoSave() {
    Message message = repo.findById(1L).orElseThrow();
    message.getTags().add("TAG1"); // INSERT
    message.setMessage("Different"); // UPDATE in DB
    entityManager.detach(message); // quickfix la panica: JPA, uit-o

    repo.save(new Message("a"));
    repo.save(new Message("b"));
    // TODO send update in DB
  }

  public void lazyLoading() {
    Message entity = repo.findById(1L).orElseThrow();
    log.info("Message: " /*+ entity*/);
  }
  // TODO performance issues & best practices cu Tx


  @Transactional(readOnly = true) // mai dramatic -> pus pe clasa
      // cel mai naiv e @Transactional pe @RestController = anathema
  // ⭐️⭐️⭐️ Pui @Transactional pe "cea mai de jos @Service poate chiar @Repository" metoda care trebuie sa faca 2+ modificari atomice in DB
  // a) permite 1st level cache .findById(<acelasiId>) x 2
  // b) lazy loading;
  // => tot tine conn blocata 1/10 = 10% din capacitate sistemului tau
  public void evitaSaFaciCaSaNuStarveziConnectionPoolul() {
    // ⚠️API call REST, SOAP, ANAF -> "ar trebui sa fie rapide" - famous last words
    // poti amana obtinerea conex JDBC pana la primul repo call cu #spring.datasource.hikari.auto-commit=false
//    restTemplate & co. poate dura f mult
    // repo.findById/.save... // abia acum iti ia conn din pool
    // urmareste metrica hikaricp_connections_acquire_seconds_sum de pe /actuator/prometheus
  }










  // Dual Write problem: DB + Messaging (Kafka, RabbitMQ, ...) in aceeasi tx
//  @Transactional
  public void saveAndSend() { // merge daca o muti in afara clasei
    altaMetoda();
    kafkaTemplateSend("mesaj"); // ✅nu e tranzactat mesajul pleaca ACUM⚠️ OrderPlacedEvent
  }

  @Transactional // nare efect daca o chemi din aceeasi clasa = nu merg proxyurile
  public void altaMetoda() {
    repo.save(new Message("Primu"));
    repo.saveAndFlush(new Message("in DB")); // INSERT acum!
  }

  private void kafkaTemplateSend(String mesaj) {
    System.out.println("kafkaTemplate.send " + mesaj);
  }
  // COMMIT in SQL DUPA❌
}
