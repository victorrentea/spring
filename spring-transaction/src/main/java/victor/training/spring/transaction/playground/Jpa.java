package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
//@Component
@RestController
public class Jpa {
  private final MessageRepo repo;
  private Long id;

  @Transactional
  public void one() {
    Message m = new Message("ONE");
    m.getTags().add("tag1");
    m.getTags().add("tag2");
    repo.save(m);
    id = m.getId();// is same as:
//    id = repo.save(new Message("ONE")).getId();
    iaopasta();
//    repo.findAll().forEach(System.out::println); // cauzeaza autoflush prematur
    h();
//    repo.flush(); // sau asa
    log.info("End of method ---");
  } // write-behind: DUPA ce ies din metoda TxInterc face FLUSH(trimite IN DB insert/update/delete) + COMMIT

  private void iaopasta() {
    repo.saveAndFlush(new Message("TWObis")); // forteaza FLUSH dupa insert.
  }

  private void h() {
    g();
  }

  private void g() {
    f();
  }

  private void f() {
    repo.saveAndFlush(new Message("TWO"));
  }

  @GetMapping // daca esti intr-un HTTP request, Spring tine conexiune deschisa pt tine pana la finalul requestului
  // lazy load merge via HTTP dar nu daca incepi fluxul din
  // @Scheduled sau @Async sau Rabbit/Kafka listener, @EventListener(ApplicationStartedEvent.class)
  @Transactional(readOnly = true) // daca vrei lazy loading sa mearga, e mai cinstit asa
  public Long two() {
    Message e = repo.findById(1L).orElseThrow();
    e.setMessage("schimbare"); // nu se duce in DB ca e este 'detasata'
    System.out.println("---");
    System.out.println(e.getTags());
    return e.getId();
  }

}
// met async nu mosteneste th cu tranzactie
// daca cine te-a chemat vrea sa-ti astepte returnul, el tot va ASTEPTA sa termini.