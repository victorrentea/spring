package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Playground {
    private final AnotherClass other;

    private final MessageRepo repo;
    private final EntityManager em;
    private final MyBatisMapper mybatis;
    private final NamedParameterJdbcTemplate namedJdbc;
    private final JdbcTemplate jdbc;
    private final DataSource dataSource;

    // JDBC

    @Transactional
    public void transactionOne() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 100);
        map.put("name", "ALO");

        namedJdbc.update("insert into MESSAGE(id, message) values ( :id, :name )",map);

        final List<Message> list = jdbc.query("SELECT id, message FROM MESSAGE", (rs, rowNum) -> {
            long id = rs.getLong("id");
            String message = rs.getString("message");
            return new Message(id, message);
        });
        System.out.println(list);
//        if (true) {
//            throw new RuntimeException();
//        }

        jdbc.update("insert into MESSAGE(id, message) values ( 101,? )", "ALO");
//        mybatis.search(100);
        repo.save(new Message("jpa"));

        System.out.println("Cica s-a scris ");
        repo.flush();
        System.out.println(mybatis.search("jpa"));

        System.out.println("se termina metoda");
    }

    @Transactional(rollbackFor = Exception.class)
    public void transactionTwo() throws Exception {
        // TODO Repo API
        // TODO @NonNullApi

        other.salonulOval();
        try {
            other.apelHttp();
        } catch (Exception e) {
//            other.persistErorr(e.getMessage()); //merge!

            transactionTemplate.execute(status -> {
                // ai o noua tx separata
                this.persistErorr(e.getMessage()); // nu merge pt ca apelurile locale NU SUNT PROXIATE
                return null;
            });
            // magia merge in spring (proxy) doar daca mergi la metoda printr-o referinta injectate de spring.
//            throw e;
        }
        System.out.println("Aici ies");
    }
//    @Transactional(propagation = Propagation.REQUIRES_NEW) == degeaba daca o chemi local.
    public void persistErorr(String message) {
        repo.save(new Message("Eroare: " + message));
    }


    private TransactionTemplate transactionTemplate;

    @Autowired
    public void setTransactionTemplate(PlatformTransactionManager transactionManager) {
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(Propagation.REQUIRES_NEW.value());

    }

    @Transactional(readOnly = true) // stops auto-flush (NO UPDATES)
    public void transactionThree() throws Exception {

        Message message = repo.findById(101L).get();
        message.setMessage("din readonly");

        // repo.save(new Message("ceva"));
    }

    public void querying() {
        // NICIODATA nu hardcodezi JPQL FIXAX (constant) in cod --> @NamedQueries (JPA) sau mai bin @Query (spring) ca sa le poata valida la pornire
//        TypedQuery<Message> query = em.createQuery("SELECT m FROM Message m WHERE m.message = ?1", Message.class);
//        query.setParameter(1, "ALO");
//        List<Message> messages = query.getResultList();

        List<Message> messages = repo.findWithMessage("ALO");

        System.out.println(messages);

        System.out.println(repo.findByMessageLike("%L%"));

    }

}

@Service
@RequiredArgsConstructor // generates constructor for all final fields, used by Spring to inject dependencies
class AnotherClass {
    private final MessageRepo repo;
    @Transactional
    public void salonulOval() {
        Message message = repo.findById(100L).get();
        message.setMessage("Alt mesaj");

    }


    @Transactional
    public void apelHttp() throws Exception {
        throw new NullPointerException();
        // Testul meu:
//        throw new Exception("Exc Checked");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void persistErorr(String message) {
        repo.save(new Message("Eroare: " + message));
    }
}