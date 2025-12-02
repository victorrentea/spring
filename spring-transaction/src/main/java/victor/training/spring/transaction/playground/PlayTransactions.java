package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import org.springframework.transaction.support.TransactionTemplate;
import victor.training.spring.transaction.TransactionalMindit;

import javax.sql.DataSource;
import java.io.IOException;

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

  @Transactional
  public void play() {
    repo.save(new Message("JPA"));
    repo.save(new Message("JPA2"));
  }
}

@Service
@RequiredArgsConstructor
class OtherClass {
  private final MessageRepo repo;
}

// TODO
//  0 p6spy
//  1 Cause a rollback by breaking NOT NULL/PK/UQ, throw Runtime, throw CHECKED
//  2 Tx propagates with your calls (in your thread😱)
//  3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
//  4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
//  5 Performance: connection starvation issues : debate: avoid nested transactions
