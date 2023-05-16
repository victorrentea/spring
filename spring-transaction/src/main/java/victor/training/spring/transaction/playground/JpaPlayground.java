package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class JpaPlayground {
    private final MessageRepo repo;
    private Long messageId;

    @Transactional
    public void transactionOne() {
        messageId = repo.save(new Message("unu")).getId();
        repo.save(new Message("doi"));

        System.out.println(repo.count());  // orice SQL faci in DB, JPA face auto-flush

        log.info("Ies din metoda- apare in log INAINTE DE INSERTURI aka Write Behind (JPA)");
        // JPA AMANA inserturile pana inainte de commit cu speranta sa faca = FLUSH
        // 1) ca nu mai sunt necesare (pesimist) ROLLBACK
        // 2) batchuiasca impreuna inserturile, daca ii dai voie
    }

    @Transactional
    public void transactionTwo() {
        // change the message field of the Message @Entity with id = messageId field
        Message message = repo.findById(messageId).orElseThrow();
        message.setMessage("Altu'");
//        repo.save(message); // nu e necesar daca esti intr-o @Transactional
        // proxy la final face auto-flush de dirty changes
    }
}