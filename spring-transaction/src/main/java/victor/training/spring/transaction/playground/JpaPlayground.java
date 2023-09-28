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
    repo.save(new Message("JPA"));

    log.info("✉️ Send Rabbit/email/SMS/RMI/SOAP. id:" + mess.getId());
  }
  // 🐞 UK violation sare DUPA ce iesi din DB, cand trimite JPQ INSERTURILE chiar inainte de commit
  //    dar mesajul a ramas trimis

  public void transactionTwo() {}
}