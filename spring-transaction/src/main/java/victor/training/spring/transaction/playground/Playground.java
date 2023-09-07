package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class Playground {
  private final MessageRepo repo;
  // tx management low level:
//    DataSource ds;
//    Connection connection = ds.getConnection();
//    connection.setAutoCommit(false); // open a TX
//    // INSERT ,,, your code
//    connection.commit();

//  @Transactional //
  // acquire a JDBC Connection from the connection pool (size=10) Hikari
  // bind the connection to the CURRENT THREAD until the end of the method
  // start a transaction on that connection using connection.setAUtoCommit(false)
  // creating a PersistenceContext on the THREAD
  public void transactionOne() {
    var dataFromRemote = "data retrieved via an API call";
    // restTemplate.getForObject / webClient.block() => JDBC Connection Pool Starvation

//    Playground myself = (Playground) AopContext.currentProxy(); // #2 also this!! OMG

    other.atomicPart(dataFromRemote);
//    transactionTemplate.executeWithoutResult(status -> { // #4 programatic no aspects
//      repo.save(new Message("JPA with " + dataFromRemote));
//      repo.save(new Message(null));
//    });
  }
  private TransactionTemplate transactionTemplate;
  @Autowired
  public void initTxTemplate(PlatformTransactionManager transactionManager) {
    transactionTemplate = new TransactionTemplate(transactionManager);
    transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    transactionTemplate.setTimeout(1);
  }
  @Autowired
//  @Lazy
//  private Playground myself; // proxy injected here ! ME #3
  private OtherClass other; // #1 move method to other class and inject it proxy injected here !
    // breaking complexity apart is generally GOOD

//  @Transactional
//  public void atomicPart(String dataFromRemote) {
//    repo.save(new Message("JPA with " + dataFromRemote));
//    repo.save(new Message(null));
//  }

  @Transactional//(propagation = )
  public void transactionTwo() throws IOException {
    Message message = repo.findById(1L).orElseThrow();
    message.setMessage("updated"); // auto-flushing dirty changes
//    repo.flush();
//    throw new IllegalArgumentException("BR violation"); // ROLLBACK

    System.out.println("Before tjhe second findById ");
    Message secondFetch = repo.findById(2L).orElseThrow(); // fetched from memory the same == instance as before
    // 1st level cache
    System.out.println("Fetch again an etity modified before in the " +
        "same current tx:" + secondFetch.getMessage());
    System.out.println(message ==secondFetch);

    System.out.println("All: " + repo.findAll());


    throw new IOException("BR violation"); // causes COMMIT <- MISTAKE
    // WHY?! if it's a checked exception, someone is gonna handle it
    // REAL REASON : Legacy.
    // it was 2005~ EJB 2.x was ruling the world full of XML interface and corporate bullshit (worst standards in history)
    // the developers rioted against EJB and some created SPring.
    // 99% of marketshare was EJB.
//    Spring had to use the same approach to ease the migration of EJB apps to Spring.
    // Moral: never ever ever ever throw checked exceptions. They are a mistake in the Java Language
  }

  @Transactional(readOnly = true) // blocks the conn for too long
  public void transactionThreeReadOnlyForLazyLoading() {
    Message message = repo.findById(1L).orElseThrow();
    System.out.println("Got the message");
    System.out.println("Phone:" + message.getPhones()); //  LAZY     LOADING
  }
  // 1. hibernate is not designed for readonly work => don't try to make @Entity immutable. you will die
  // 2. instead of lazy loading is preferable to pre-fetch all the data you need upfront, perhaps with wsome
  // @Query (LEFT JOIN FETCH) jpql + release the conn earlier or
  //
  // -====> "Light" CQRS:
  // - read model jpql="select new ... SEarhcResultReponse) different than
  // - WRITE MODEL (@Entity)
}

@Service
@RequiredArgsConstructor
class OtherClass {
  private final MessageRepo repo;
  private final Third third;

  @Transactional // Tx1 is started by this proxy
  public void atomicPart(String dataFromRemote) {
    repo.saveAndFlush(new Message("JPA with " + dataFromRemote));
    // forces a TCP-IP request RIGHT NOW

    inTheSameThreadTheTxPropagatesAutomatically();
    System.out.println("AFTER the 2 .save"); // Write-behind: JPA buffers all the changes for the end of Tx
    third.method();
  }

  private void inTheSameThreadTheTxPropagatesAutomatically() {
    repo.save(new Message("SECOND"));
  }
}


@RequiredArgsConstructor
@Service
class Third {
  private MessageRepo repo;

  @Async // logs automatically any exception if method retunrs "void" (fire-and-forget)
  public void method() {
    repo.save(new Message(null));
  }
}


// TODO
// 0 p6spy
// 1 Cause a rollback by breaking NOT NULL/PK/UQ, throw Runtime, throw CHECKED
// 2 Tx propagates with your calls (in your thread😱)
// 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
// 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
// 5 Performance: connection starvation issues : debate: avoid nested transactions
