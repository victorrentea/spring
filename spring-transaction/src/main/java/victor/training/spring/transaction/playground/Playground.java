package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class Playground {
  private final MessageRepo repo;
  private final OtherClass other;

  // Spring Data JPA: ✅JpaRepository + @Query
  // JPA(Hibernate)  Session🪦, EntityManager🪦, @Entity✅
  // JdbcTemplate 2010 imbraca SQL nativ - ✅ DOAR daca nu ai JPA
  // JDBC🪦 90' Connection, ResultSet, PreparedStatement
//  @TransactionAttribute
//      (rollbackFor = Exception.class)  // solutia 1
  // Solutia 2: RENUNTA sa mai arunci CheckedExceptions <- sunt greseli oricum in limbaj; arunci doar runtime

  // message listener JMS
  @Transactional
  public void transactionOne() throws IOException {
    repo.save(new Message("JPA"));
    try {
      other.bizLogicAtomic();
    } catch (Exception e) {
      // Change: trebuie INSERT eroare in tabela ERRORS;
      // Dev: NU! pun in log.error
      // Biz: da vreau separata
      // Dev: NU! cauta-n loguri. iti pun un prefix [VALEU-0]. ridica-ti alerte
      // Dev: sau iti trimit mesaj cu eroare pe vreo alta coada (DLQ)
      saveError(e);
    }
  }

  // Tzeapa KING din proxyuri: apelurile
  // locale in acceasi clasa nu trec prin proxy
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void saveError(Exception e) {
    repo.save(new Message("EROARE: " + e.getMessage()));
  }

  public void transactionTwo() {}
}
@Service
@RequiredArgsConstructor
class OtherClass {
  private final MessageRepo repo;
  public void pasu2() {
    repo.save(new Message("NULL"));
//    if (true) throw new IllegalArgumentException("Ceva de biz");

    try {
      codLegacy();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e); // asa da, ne respectam. doar runtime
    }
    // ce puii mei se intampla!? o exceptie checked (throws...) lasa tranzactia sa se comita !?!
    // ce dobitoc a crezut ca e o idee buna sa faci COMMIT daca FileNotFoundException
    // toata lumea care vede asta tzipa. si se intreaba? de ce ?!?!?!!!?!?!?!?!?
    // Motivul este unul ISTORIC.

    // Era 2005-2007, pe-afara ploua cu EJB 2.x cu muuuuuult XML🤢
    //  cel mai urat standard din lumea java din istorie

    // Intr-o padure Rod Johnson si haiducii lui  faceau un Framework
    // sa lupte cu prostia EJB 2. 10x mai simplu.

    // Iesiti din padure au fost insa incojurati de EJB de la care trebuiau
    // sa fure OAMENI.
    // Springu a trebuit sa faca tranzitia cat mai usoara ca sa poata fura
    // proiect EJB -> migreze -> Spring;
    // Springu a copiat TAMPENIA asta cu checked exceptions = COMMIT de la EJB
    // replace "@TransactionAttribute" cu "@Transactional"
  }

  private static void codLegacy() throws FileNotFoundException {
    if (true) throw new FileNotFoundException("N-am gasit fisieru!");
  }

  @Transactional // acest proxy vede iesind din metoda o ex runtime
  // distruge tranzactia curenta.
  // PANICA ESTE INSA CA TX CURENTA i-a venit de la altu
  public void bizLogicAtomic() {
    repo.save(new Message("Customer"));
    repo.save(new Message("Audit"));
    throw new IllegalArgumentException("ERoare de biz!");
  }
}
// TODO
// 0 p6spy
// 1 Cause a rollback by breaking NOT NULL/PK/UQ, throw Runtime, throw CHECKED
// 2 Tx propagates with your calls (in your thread😱)
// 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
// 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
// 5 Performance: connection starvation issues : debate: avoid nested transactions
