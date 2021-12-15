package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo messageRepo;
    private final EntityManager entityManager;
    private final JdbcTemplate jdbcTemplate;
    private final AnotherClass other;
    private final MyBatisMapper mybatis;

//    @Transactional
//    public void method() {
//        // pt migrari de proiecte la spring care foloseau direct sql.Connection
//        // antipattern petru proj noi
//        DataSource ds;
////        Connection conn = ds.getConnection();
//        Connection connection = DataSourceUtils.getConnection(ds);
//        connection.setAutoCommit(true);
//        connection.prepareStatement("INSERT INTO ...").executeUpdate();
//        connection.prepareStatement("INSERT INTO ...").executeUpdate();
////        connection.commit();
//    }


    @Transactional
    public void transactionOne() {
        jdbcTemplate.update("insert into MESSAGE(id, message) values ( 100,? )", "ALO");
//        try {
            other.metoda();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        messageRepo.save(new Message(null));
    }
    @Transactional
    public void transactionTwo() {
        // TODO Repo API
        // TODO @NonNullApi
    }
    public void proxyulSpringDeTransactional_pseudocod() {
        // if (propagation=REQUIRES_NEW && am tx pe thread) { suspend Tx si iau conn nou }
        // conn = datasource.getConnection();
//        startTranscation = conn.setAutocommit(false)
//        try {
//          apelulReal();
//        }catch (Excetion ) {conn.rollback()}
//        commit();
    }
}

@Service
@RequiredArgsConstructor // generates constructor for all final fields, used by Spring to inject dependencies
class AnotherClass {
    private final MessageRepo repo;
    private final EntityManager entityManager; // JPA

    @Transactional(propagation = Propagation.NOT_SUPPORTED) // autocommit
//    @Transactional(propagation = Propagation.REQUIRES_NEW) //
    public void metoda() {
//        entityManager.persist(new Message("JPA"));
        repo.save(new Message("cu spring data care-si face Tx singur"));
//        throw new IllegalArgumentException("Crapa-i-ar capul");
    }
}