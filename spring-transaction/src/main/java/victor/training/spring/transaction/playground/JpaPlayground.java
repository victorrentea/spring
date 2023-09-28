package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class JpaPlayground {
  private final MessageRepo repo;

  @Transactional
  public void transactionOne() throws IOException {
    Message mess = new Message("JPA");
    repo.save(mess); // Write-Behind: INSERTul merge in baza doar la FLUSH (mai tarziu), chiar inainte de COMMIT
    // Motive : 1) "ca poate nu le fac ca arunca ex", 2) batching (performance)
//    repo.save(new Message("JPA"));
//    repo.saveAndFlush(new Message("JPA")); // #2 saveAndFlush

//    repo.flush(); // #1 manual

    // #3 JPA va face automat flush la changeuri daca trimiti in DB un SELECT
    log.debug("n=" + repo.count()); // cauzeaza FLUSH -> Exceptie la noi

    log.info("✉️ Send Rabbit/email/SMS/RMI/SOAP. id:" + mess.getId());
  }
  // 🐞 UK violation sare DUPA ce iesi din DB, cand trimite JPQ INSERTURILE chiar inainte de commit
  //    dar mesajul a ramas trimis


  @Transactional
  public void transactionTwo() {
    Message message = repo.findById(1L).orElseThrow();
    message.setMessage("Updated"); // orice modifica pe starea unui @Entity in
    // cadrul unei @Transactional se scrie automat inapoi in DB (dirty check) la final de Tx

    // Visul "Repository design pattern":
    // cand lucrezi cu un repo sa para ca obiectele raman in memorie persistate

    // Realitatea: devi modifica "din greseala" entitati care raman persistate asa in DB
    // PTSD (Post Trauma) => interzici asa ceva, si obligi echipa sa faca mereu .save
    // Opt1: go with the pattern: changeuri in @Transactional
    // Opt2: old-school: nu @Transactional unde modifici, ci repo.save(updatedEntity) explicit
  }
}