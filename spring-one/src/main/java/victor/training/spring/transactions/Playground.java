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


    @Transactional // PersistenceContext atasat
    public void transactionOne() {
        jdbcTemplate.update("insert into MESSAGE(id, message) values ( 100,? )", "ALO");
        messageRepo.save(new Message("Primul insert"));
//        System.out.println(messageRepo.findAll());
//        entityManager.flush(); // doar cand combini JPA cu PL/SQL
        System.out.println(messageRepo.countNativ());
//        jdbcTemplate.update("select count(*) from MESSAGE", "ALO");
        try {
            other.metoda();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
//        messageRepo.save(new Message(null));
        System.out.println("ies din metoda");
    }
//    @Transactional
    public void transactionTwo() {
        Message message = messageRepo.findById(100L).get();
        message.setMessage("Alt mesaj");
        messageRepo.save(message); // daca nu iti place auto-flush, scoti @Transactional si faci .save explicit (merge de fapt in JPA)
        System.out.println("Dupa iesirea din fct");
    }// la final, toate @Entity incarcate in @Transactional, daca au fost modificate
    // se scriu la loc in baza cu UPDATE = aka "Dirty check pe entitati atasate"

    // cu JPA, la inchiderea TX, se face:
    // 1 se trimit toate INSERT-urile (din spatele unui persist/save) ==> periculos
    // 2 se UPDATE entitatile modificate in tx, mai putin cand ai @Tx(readOnly=true) sau faci entityManager.detach(entity)

    public void proxyulSpringDeTransactional_pseudocod() {
        // if (propagation=REQUIRES_NEW && am tx pe thread) { suspend Tx si iau conn nou }
        // boolean createdTx = ....
        // conn = datasource.getConnection();
//        startTranscation = conn.setAutocommit(false)
//        try {
//          apelulReal();
//            if (createdTx) commit();
//        }catch (Excetion ) {

//          conn.rollback()
//          }
//
    }
}

@Service
@RequiredArgsConstructor // generates constructor for all final fields, used by Spring to inject dependencies
class AnotherClass {
    private final MessageRepo repo;
    private final EntityManager entityManager; // JPA

//    @Transactional(propagation = Propagation.NOT_SUPPORTED) // autocommit
    @Transactional(propagation = Propagation.REQUIRES_NEW) //
    public void metoda() {
//        entityManager.persist(new Message("JPA"));
        repo.save(new Message("cu spring data care-si face Tx singur"));
//        ThreadUtils.sleep(1000);
//        rest.get() // 5 sec > potential perf issue ca blochezi cat astepti responseul 2 conexiuni odata!
        throw new IllegalArgumentException("Crapa-i-ar capul");
    }
}