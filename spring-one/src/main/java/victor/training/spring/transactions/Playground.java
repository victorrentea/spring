package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import victor.training.spring.ThreadUtils;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;
    private final EntityManager em;
    private final JdbcTemplate jdbc;
    private final AnotherClass other;
    private TransactionTemplate txTemplate;
//    private OurTransactionTemplate newTransaction;

    @Autowired
    public void setTxTemplate(PlatformTransactionManager txManager) {
        this.txTemplate = new TransactionTemplate(txManager);
        txTemplate.setPropagationBehaviorName("REQUIRES_NEW");
        txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
    }

    //    @Autowired
//    private Playground myself;
//    @Transactional
    public void transactionOne() {
        txTemplate.execute(status -> {
            atomicaFrate();
            return null;
        });
        other.parpad1();
        ThreadUtils.sleep(300);

        repo.save(new Message("frumos"));
    }

//    @Transactional
    public void atomicaFrate() {
        jdbc.update("insert into MESSAGE(id, message) values ( 100, ? )", "ALO");
        repo.save(new Message("null"));
        jdbc.update("insert into MESSAGE(id, message) values ( 105, null )");
    }

    @Transactional
    public void transactionTwo() {
        // TODO Repo API
        // TODO @NonNullApi
    }
}
@Service
@RequiredArgsConstructor // generates constructor for all final fields, used by Spring to inject dependencies
class AnotherClass {
    private final MessageRepo repo;
    @Async
    public void parpad1() {
        System.out.println("Cate mesaje vad in baza " + repo.count());
        repo.save(new Message("parpad"));
        ThreadUtils.sleep(1000);
    }
}