package victor.training.spring.transaction.playground;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Slf4j
@RequiredArgsConstructor
@Component
public class PlayJpa {
  private final MessageRepo repo;
  private final EntityManager entityManager;
  private final ApplicationEventPublisher applicationEventPublisher;

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
  @Transactional
  public void saveAndSend() { // merge daca o muti in afara clasei
    repo.save(new Message("Primu"));
    repo.saveAndFlush(new Message("in DB")); // INSERT acum!
    applicationEventPublisher.publishEvent(new EventuMeu("mesaj"));
    repo.save(new Message("OUTBOX table un rand cu mesajul de trimis"));
  }
  public record EventuMeu(String mesajDeTrimis){}

  // in-mem e riscant:
  // 🙁 daca pica kafka send ❌, DB a facut commit✅ = inconsistenta
  // 🙁 daca pica podul meu pana apuca sa ruleze dupaCommit() = pierzi event
  @TransactionalEventListener(phase = AFTER_COMMIT) // ruleaza dupa COMMITul tx din care s-a facut .publish
  public void dupaCommit(EventuMeu event) {
    kafkaTemplateSend(event.mesajDeTrimis()); // ✅nu e tranzactat mesajul pleaca ACUM⚠️ OrderPlacedEvent
    // dureaza 10ms, iar k8s te lasa  3 x 5sec (interval de liveness) pana-ti da kill pod
    // iar brokerul de kafka nu pica
    // ! AVRO serialization failed
  }
  // daca vrei super robust: in loc de .publish() faci
  // outboxRepo.save(new MesajDeTrimis(in db)) si-i dai COMMIT cu restul
  // a) @Scheduler 1/sec incerci sa trimiti > delete
  // b) debezium.io/CDC: un plugin in DB trimite direct din DB in kafka

  private void kafkaTemplateSend(String mesaj) {
    System.out.println("kafkaTemplate.send " + mesaj);
  }
  // COMMIT in SQL DUPA❌


  // ce ma fac daca asta ruleaza pe 3 poduri
  @Scheduled(fixedRateString = "1000")
//  @SchedulerLock(name = "TaskScheduler_scheduledTask",
//      lockAtLeastFor = "PT5M", // rate minim
//      lockAtMostFor = "PT14M") // ⭐️printr-o tabela pe care ti-o creezi in DB
  // se asigura ca nu porneste un alt POD metoda asta min(cat ea ruleaza,lockAtMostFor)
  public void poll() throws InterruptedException {
    log.info("START cu DB (una) din 3 poduri");
    // redis lock, SELECT FOR UPDATE, .... ..... . STOP! => use shedlock
    Thread.sleep(1300);
    log.info("END");
  }

//  @Scheduled(fixedRateString = "1000") // bate separat pe th lui
//  public void poll2() throws InterruptedException {
//    log.info("START2");
//    Thread.sleep(1300);
//    log.info("END2");
//  }
}
