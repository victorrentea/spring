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

    @Transactional
    public void transactionOne() {
        repo.save(new Message("unu"));
        repo.save(new Message("unu"));
        log.info("Ies din metoda- apare in log INAINTE DE INSERTURI aka Write Behind (JPA)");
        // JPA AMANA inserturile pana inainte de commit cu speranta sa faca 
        // 1) ca nu mai sunt necesare (pesimist) ROLLBACK
        // 2) batchuiasca impreuna inserturile, daca ii dai voie
    }

    @Transactional
    public void transactionTwo() {
    }
}