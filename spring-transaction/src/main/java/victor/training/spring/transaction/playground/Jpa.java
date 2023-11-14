package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class Jpa {
  private final MessageRepo repo;
  private Long id;
//  private final EntityManager entityManager; JPA direct

  @Transactional
  public void one() {
    Message message = repo.save(new Message("JPA"));
    id = message.getId();
    log.info("End of method. inserted: " + id);
    // in log INSERT apare dupa iesirea din functie
    // Write-Behind = JPA amana INSERT/UPDATE in DB pana inainte de COMMIT. Atunci le face 'flush'
    // PROS: poate evita sa faca INSERT daca ROLLBACK; poate BATCHEUI impreuna mai multe inserturi
    // CONS: misleading. poate crapa UQ DUPA CE IESI din metoda.
    // poti evita featureul facand repo.saveAndFlush
  }

//  @Transactional
  public void two() {
//  Repository pattern: JPA incearca sa-ti dea iluzia ca obiectele din Repo vin si raman in memorie
    Message e = darkDeepMethod();
    e.setMessage("Different");
    // Auto-flush changes: la finalul unei Tx, JPA face automat UPDATE pe toate @Entity dirty
    log.info("Am scos entity cu id " + e.getId());
    log.info("Cu tagurile " + e.getTags()); // lazy loading -> SELECT
  }
  // daca acest endpoint e chemat din HTTP, Spring Boot tine conex deschisa chiar dupa ce s-a terminat tranzactia

  @GetMapping("lazy")
  @Transactional(readOnly = true)
  public String http() {
    var e = darkDeepMethod();
    log.info("Cu tagurile " + e.getTags());
//    rest.apicall
    return "toStringul a cerut SELECT : " + e.getTags();
  }

  private Message darkDeepMethod() {
    return repo.findById(id).orElseThrow();
    // conn is now release immediately after the SELECT
  }
}
