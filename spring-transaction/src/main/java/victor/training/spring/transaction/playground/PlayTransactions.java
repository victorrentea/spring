package victor.training.spring.transaction.playground;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;


// spring va injecta tutoror care cer o PlayTransaction o instanta din proxy
// generat dinamic cu CGLIB (Code Generation LIB):
//class PlayTransactionsSubclasaGenerateDeCGLIB extends PlayTransactions {
//  @Override
//  public void play() {
//    // start tx
//    playTransactionRealInstance.play();
//    // commit tx
//    // }catch (RuntimeException) { rollback tx}
//    // }catch (Exception) { commit tx} 😱😱😱
//  }
//}

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayTransactions {
  private final JdbcTemplate jdbcTemplate; // 2001
  private final EntityManager entityManager; // 2006
  private final MessageRepo repo; // = Spring Data JPA, 2011
  private final OtherClass other;
  private final TransactionTemplate transactionTemplate;
  // alternativa FP la AOP (@Transactional)
//    transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//    transactionTemplate.execute(s-> {
//      // 1 INSERT
//      // 2
//      return null;
//    });

  //  @TransactionalMindit
//  @Transactional
//  public void play() throws IOException {
//    jdbcTemplate.update("insert into MESSAGE(id, message) values (100,'SQL' )");
//    jdbcTemplate.update("insert into MESSAGE(id, message) values (101,'SQL2' )");
//    repo.met("sql3");
//    Message messageWithId = repo.save(new Message("JPA"));
//    log.info("Deja i-a pus id, chiar daca nu-i INSERT inca:" + messageWithId);
//    repo.save/*AndFlush*/(new Message("JPA2")); // #1

  ////    repo.flush(); // #2 sau asa
//    System.out.println(repo.count());// #3 inainte de orice SQL lansat in DB
//
//    log.info("'JPA Write-Behind' dupa ce ies, @Transactional vrea sa dea commit. inainte de asta, face flush la toate schimbarile ramase 'de trimis in DB'");
//    // 😊 performance🔼: ca poate nu-i nevoie. si sa trimita in batchuri daca activezi JDBC batching spring.jpa.properties.hibernate.jdbc.batch_size = 100
//    // 🙁 debugging🔽: daca INSERT SQL crapa cu PK/UK/NOT NULL/FK => vezi exceptia DUPA ce iesit din functie, tarziu. greu de traceuit.
//  }

  // Springu extinde clasa asta (PlayTransactions) la runtime in care face
  // override la metodele tale, pt a putea executa cod INAINTE ± DUPA apelul efectiv
  // ce alte adnotari Spring standard mai intercepteaza apeluri de metode = Spring AOP
  // @Secured("ROLE_ADMIN") / @PreAuthorized("..") -> cere sa fii admin
  // @Timed / @Observed: (micrometer) masori timpul de executie ca metrica pe /actuator/prometheus
  // @Cacheable: daca chemi metoda din nou cu aceeasi param -> aceleasi result
  @Transactional // cum merge? -> trebuie ca spring sa intercepteze apelurile acestei metode
  public void play() {
    repo.save(new Message("JPA")); // 😱 are el intern @Transactional
    // => NU ai nevoie de @Transactional pe metoda ta daca faci un singur save
    //extracted(); // apelul local (in aceeasi clasa) nu trece prin proxy! ⚠️⚠️⚠️⚠️⚠️


  }


  @Transactional//(noRollbackFor = MyRuntimeException.class)
  public void entry() {
    play();
    try {
      other.extracted();
    } catch (MyEx ignored) {
      log.warn("Swallow");
    } // swallow ex: cum sa enervezi senioru
    repo.save(new Message("Dupa"));
  }
}
class MyEx extends RuntimeException {}

@Service
@RequiredArgsConstructor
@Transactional
class OtherClass {
  private final MessageRepo repo;

  //  @Transactional(propagation = Propagation.REQUIRED) // default: propaga daca e, sau creeaza daca nu-i
//  @Transactional // default: propaga daca e, sau creeaza daca nu-i
//  @Transactional(propagation = Propagation.REQUIRES_NEW) // tx 2 e ROLLBACK
//  @Transactional// introduce un proxy care marcheaza tx activa acum ca "ROLLBACK ONLY"

  @Transactional(propagation = Propagation.NOT_SUPPORTED) // chiar daca pe clasa cica vor toate metodele tx, asta nu vrea
  // daca sare vreo ex runtime, nu strica tx callerului
  public void extracted() {
    repo.save(new Message("JPA2"));
    if (true) throw new MyEx();
  }
}

// TODO
//  0 p6spy
//  1 Cause a rollback by breaking NOT NULL/PK/UQ, throw Runtime, throw CHECKED
//  2 Tx propagates with your calls (in your thread😱)
//  3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
//  4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
//  5 Performance: connection starvation issues : debate: avoid nested transactions
