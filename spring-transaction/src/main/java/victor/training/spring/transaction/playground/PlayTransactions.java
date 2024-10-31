package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.sql.DataSource;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayTransactions {
  private final DataSource dataSource; // 1998
  private final JdbcTemplate jdbcTemplate; // 2001
  private final EntityManager entityManager; // 2006
  private final MessageRepo repo; // = Spring Data JPA, 2011
  private final OtherClass other;


//  @Scheduled(cron = "0 0 0 * * *")
  @SneakyThrows
  @Transactional //(propagation = REQ)
  public void play() {
    // 1. helper al springului peste SQL ca sa nu pui tu mana pe Connection, DataSource, SQLException, .commit
    jdbcTemplate.update(
        "insert into MESSAGE(id, message) values (100,'SQL' )");
    eventPublisher.publishEvent(new DupaComitEvent("trimite un mai lui Alex Ilie"));
    // 2. JPA/Hibernate(ORM) folosit prin Spring Data JPA
    repo.saveAndFlush(new Message("JPA")); // nu pleaca nici un INSERT inca spre DB
    // hibernate face flush la entitati inainte de a face select
    repo.count(); // intai  flush=insert; SELECT COUNT(*) FROM MESSAGE
    log.info("Ies din metoda");
  } // dupa ce iesi din metoda te intorci la TransactionInterceptor cauzat de @Transactional

  private final ApplicationEventPublisher eventPublisher;

  record DupaComitEvent(String text){}
  @TransactionalEventListener
  public void dupaCommit(DupaComitEvent event) {
    log.info("Ruelaza dupa comit: " + event.text());
  }

  // care incearca sa faca COMMIT la tot ce ai facut.
  // Atunci el insa se prinde ca JPA inca nu a facut flush la entitati in DB
  // si face intai inserturile in DB, apoi face commit-ul
  // de ce amana inseturile JPA-ul = "Write-Behind"
  // 1) ca poate nici nu nevoie sa le scrie (ca sare exceptie)
  // 2) pentru a le putea batheui impreuna (JDBC Batching) - cand importi fisiere


//  @Transactional// by default face rollback doar pt alea runtime
  // ce BOU a facut asta?? > EJB , si Springu l-a imitat!
  // era 2005. Ploua afara cu rahat. Cel mai urat standard din Java: EJB 2.x
  // Intr-un codru des Ron facea Spring Framework
  // toate corporatiile mari foloseau EJB 2.x
  // Ron a vrut sa converteasca developerii existenti la Spring
  // a facut @Transactional sa imite comportamentul lui @TransactionAttribute

  // tl;dr: NU MAI ARUNCA NICIODATA EXCEPTII CHECKED
//  @Transactional(rollbackFor = Exception.class) // fix


  @Transactional
  public void andrei() throws BusinessException {
    log.info("Asta-i pentru Andrei");
    Message message = repo.findById(1L).orElseThrow();
    log.info("oare ent e curata" + message.getClass());
    message.setMessage("Ceva Diferit");
    if (false) {//biz rule!
      throw new IllegalArgumentException("No spaces allowed"); // unchecked 💖
//      throw new BusinessException("No spaces allowed"); // 🤬 checked, au murit vreo 7 ani in urma
      // o greseala a limbajului Java; NU AI VOIE SA ARUNCI EXCEPTII DECAT RUNTIME/UNCHECKED
    } else {
//      repo.save(message);
      // intr-o @Trasactional, orice modificare faci pe entitatea ta,
      // ea se duce automat in baza la final de Tx.
      // = auto flush dirty entities
      // DE CE ?
      // JPA implementeaza patternul de Repository
      // = vrea sa crezi ca obiectele cu care lucrezi NU PLEACA VREODATA DIN MEMORIE
    }

  }
}
class BusinessException extends Exception {
  public BusinessException(String message) {
    super(message);
  }
}
@Service
@RequiredArgsConstructor
class OtherClass {
  private final MessageRepo repo;
}
// TODO
// 0 p6spy
// 1 Cause a rollback by breaking NOT NULL/PK/UQ, throw Runtime, throw CHECKED
// 2 Tx propagates with your calls (in your thread😱)
// 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
// 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
// 5 Performance: connection starvation issues : debate: avoid nested transactions
