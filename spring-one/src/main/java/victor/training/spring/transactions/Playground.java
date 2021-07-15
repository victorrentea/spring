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
import java.io.IOException;

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
        txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
    }

    //    @Autowired
//    private Playground myself;
//    @Transactional
    public void transactionOne() {
        txTemplate.executeWithoutResult(status -> atomicaFrate());
        other.parpad1();
        ThreadUtils.sleep(300);

        repo.save(new Message("frumos"));
    }

//    @Transactional
    public void atomicaFrate() {
        jdbc.update("insert into MESSAGE(id, message) values ( 100, ? )", "ALO");
        repo.save(new Message("null"));
        jdbc.update("insert into MESSAGE(id, message) values ( 105, 'ceva frumos' )");
    }

//    @Transactional
    public void transactionTwo() {
        try {
            other2.work();
        } catch (Exception e) {
            other.saveError(e);
        }
    }
    private final AnotherClass2 other2;


}
@Service
@RequiredArgsConstructor // generates constructor for all final fields, used by Spring to inject dependencies
class AnotherClass2 {
    private final MessageRepo repo;
    private final AnotherClass other;
    @Transactional
    public void work() throws IOException {
        System.out.println(repo.count());
        repo.save(new Message("Inceput de drum bun."));
        repo.save(new Message("Sfarsit de drum bun."));
        System.out.println("Ies din metoda");
        System.out.println(repo.count());
        other.altaMetodaCuTransactional();
    }
}
@Service
@RequiredArgsConstructor // generates constructor for all final fields, used by Spring to inject dependencies
class AnotherClass {
    private final MessageRepo repo;
    @Transactional//(propagation = Propagation.REQUIRES_NEW)
    public void saveError(Exception e) {
        repo.save(new Message("A aparut o eroare: " + e.getMessage()));
    }
    @Async
    public void parpad1() {
        System.out.println("Cate mesaje vad in baza " + repo.count());
        repo.save(new Message("parpad"));
        ThreadUtils.sleep(1000);
    }

    @Transactional(rollbackFor = Exception.class) // aduce un proxy care rupe Tx curenta parvenita ei atunci cand iesi cu ex din metoda asta.
//    @Cacheable("degeaba")
    public int altaMetodaCuTransactional() throws IOException {
//        throw new RuntimeException("Ceva neasteptat!");
        throw new IOException("Ia uite frate, se commite desi sare exceptie din metoda cu @Transactional");
    }
}