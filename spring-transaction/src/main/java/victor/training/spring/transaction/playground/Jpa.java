package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Slf4j
@RequiredArgsConstructor
@Component
@Transactional // pt tata lumea!!
public class Jpa {
  private final MessageRepo repo; // Spring Data
  //  private final EntityManager entityManager;// modu vechi de JPA
  private Long id;

  public void one() {
//    et2();
    Message message = new Message("ONE");
    message.setHashtag(List.of("#rezist", "#coruptie", "#pnl", "#psd", "#usr", "#haur"));
    id = repo.save(message).getId();
    log.info("End of method ---"); // WRITE-BEHIND = insert apare dupa ce iesi din functie, exact inainte de COMMIT
    // 1) ca sa nu faca insert daca urmeaza rollback
    // 2) ca sa poata BATCHEUI inserturile impreuna.
    //   Batch insert ca sa reduci durata jobului de la 8h la 20min (24x)
//    throw new RuntimeException("Boom");
  }

  private void et2() {
    beci();
//        repo.flush(); // ex aici
    System.out.println("Inainte");
    System.out.println("#sieu: " + repo.findByMessage("ONE"));
    mansarda();
  }

  private void beci() {
    repo.save(new Message("ONE")); // forteaza INSERTUL in DB
    //+ crapa UQ -=>exceptie la botu' calului
    //- disableaza batching, ca trimite repede pe loc INSERTUL
  }

  private void mansarda() {
    id = repo.saveAndFlush(new Message("ONE2")).getId();
  }


  public void two() {
    log.info("entry. oare pe cine chem? ");
    try{
      other.bizFlow();
    } catch (Exception tat) {
      other.saveError(tat);
      throw tat;
    }
  }
  private final Other other;
}

@Slf4j
@Service
@RequiredArgsConstructor
class Other {
  private final MessageRepo repo;
  public void saveError(Exception tat) {
    repo.save(new Message("EROARE::: " + tat.getMessage()));
  }
    // async cand e procesare lunga ce nu vrei sa o astepti:
    // - genereaza raport (de trimis pe mail la user)
    // - import un XML/CSV/XLS de 18G
    // - ai de anuntat o promotie la emag catre 10M clienti
  // in JDBC standard NU e permisa scrierea pe acceasi conex de JDBC din > 1 thread
//  @Async
  @Transactional
  public void bizFlow() {
    log.info("biz");
    Message m = repo.findByMessage("ONE").orElseThrow();
    m.setMessage("CHANGED3");
    throw new RuntimeException("din cauza de biz. e de la ei. if credit cerut > 100KE dar sta cu mama => throw");
  }

  // @PostMapping uploadFile {
  // create temp file
  // scrie ce vine pe http in fisier
  // cheama metoda async (fiser temporar)
}
