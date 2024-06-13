package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class Playground {
  private final MessageRepo repo; // Spring Data JPA
  private final EntityManager entityManager; // JPA
  private final JdbcTemplate jdbcTemplate; // SQL
  private final OtherClass other;

  @Transactional(rollbackFor = Exception.class)
  // exceptia checked lasa tranzactia sa commita😱😱😱😱
  // pt ca exact asa facea si @TransactionAttribute din EJBulshit 2005
  // cand Spring a incercat sa-i converteasca la Spring,
  // sa migreze usor aplicatii

  // concluzii de viata:
  // 1. fa migrarea la frameworkul tau cat mai smoothie
  // 2. NU ARUNCA IN VIATA TA EXCEPTII CHECKED NICARIERI. SUNT O GRESEALA IN LIMBAJ
  public void play() throws IOException {
//    repo.findAll()// out of memory
//    repo.findAllById(List.of(1,3,4,5));// SELECT * FROM MESSAGE WHERE ID IN (1,3,4,5)
    jdbcTemplate.update("insert into MESSAGE(id, message) values (100,'a' )");
    throw new IOException("Boom");
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
