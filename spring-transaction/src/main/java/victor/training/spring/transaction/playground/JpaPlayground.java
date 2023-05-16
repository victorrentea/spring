package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;

@Slf4j
@Service
@RequiredArgsConstructor
public class JpaPlayground {
    private final MessageRepo repo;
    private Long messageId;

    @Transactional
    public void transactionOne() {
//        DataSource ds;
//        Connection connection = ds.getConnection();
//        connection.setAutoCommit(false);
//        connection.prepareStatement("DELET").execute();
//        connection.commit();
//        connection.rollback();
        messageId = repo.save(new Message("unu")).getId();
        repo.save(new Message("doi"));
        System.out.println(repo.count());  // orice SQL faci in DB, JPA face auto-flush
        log.info("Ies din metoda- apare in log INAINTE DE INSERTURI aka Write Behind (JPA)");
        // JPA AMANA inserturile pana inainte de commit cu speranta sa faca = FLUSH
        // 1) ca nu mai sunt necesare (pesimist) ROLLBACK
        // 2) batchuiasca impreuna inserturile, daca ii dai voie
//        throw new RuntimeException("Valeu");
    }

    @Transactional
    public void transactionTwo() {
        // change the message field of the Message @Entity with id = messageId field
        Message message = repo.findById(messageId).orElseThrow();
        message.setMessage("Altu'");
//        repo.save(message); // nu e necesar daca esti intr-o @Transactional
        // proxy la final face auto-flush de dirty changes
        Message message2 = repo.findById(messageId).orElseThrow();
        System.out.println("aceeasi instanta: " + (message2==message));
    }
    public void metoda3() {
        Message message = repo.findById(messageId).orElseThrow();
        log.trace("pot si eu sa fiu prost: " + message);
        // toStringul generat de lombok pune si colectiile in string =>
        // lazy load
        // crapa in orice-flux non-web daca nu ai @Transactional pe metoda
    }
}