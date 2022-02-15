package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;
    private final JdbcTemplate jdbc;
    private final EntityManager em;
    private final AnotherClass other;

//    @Transactional
    public void transactionOne() {
        repo.save(new Message("Ok"));
//        repo.flush(); // bad practice. Instead, go through JPA and it will auto-flush, as belo
//        repo.someNative();
//        em.createNativeQuery("INSERT INTO MESSAGE(ID,MESSAGE) VALUES (616, 'aaa')").executeUpdate();
        try {
            other.bigNastyLogic();
        } catch (Exception e) {
            playground .persistError(e);
        }
        repo.save(new Message("will not throw even if the current tx is ZOMBIE (kille dby the exception thrown before)"));
        System.out.println("End of method. The inserts happen AFTER this line.");
    }

    @Autowired
    private Playground playground;

    // "Each thread has its own Stack of transaction" <<
    // ThreadLocal<List<Transaction>>
    // a proxy would have its own thread - NOT RIGHT
    // all methods that you call are executed in a single thread. (unless you are using @Aync, Threads or ThreadPools .submit())

    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void persistError(Exception e) {
        repo.save(new Message("ERROR: " + e));
    }


    @Transactional
    public void transactionTwo() {
        // TODO Repo API
        // TODO @NonNullApi

        // So if a new transaction is opened in a class "instance"
        // where a transaction already exists,
        // no other transaction will be created -
        //
        // is it because of the relation of a transaction to a thread?
    }
}


@Service
@RequiredArgsConstructor // generates constructor for all final fields, used by Spring to inject dependencies
class AnotherClass {
    private final MessageRepo repo;



//    @TransactionAttribute(REQUIRES_NEW) // EJB
    @Transactional (rollbackFor = Exception.class) // fight the legacy FROM EJB since 20+ years ago.
    public void bigNastyLogic() throws IOException { //
        // Checked exceptions are NEVER to be used. are a mistake in the langue
        repo.save(new Message("Stuff1"));
        repo.save(new Message("Stuff2"));

        if (true) throw new IOException("BUM!");
    }
}