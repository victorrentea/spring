package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;
    private final EntityManager entityManager;
    private final JdbcTemplate jdbc;
    private final OtherClass other;

    @Transactional
    public void transactionOne() {
        jdbc.update("insert into MESSAGE(id, message) values ( 100,'ALO' )");
        System.out.println("😁");

        System.out.println(repo.findAllByMessage("ALO"));
        try {
            other.bizLogicRiscant();
        } catch (Exception e) {
            other.persistError(e);
        }
    }

    @Transactional
    public void transactionTwo() {
    }
}

@Service
@RequiredArgsConstructor
class OtherClass {
    private final MessageRepo repo;

    @Transactional // atomic , dar vine si proxy aici.
    public void bizLogicRiscant() {
        repo.save(new Message("INSERT tranzactia"));
        repo.save(new Message("UPDATE count.amount -- "));
        throw new RuntimeException("BUM");
    }

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void persistError(Exception e) {
        e.printStackTrace();
        Message errorToInsert = new Message("ERROR: " + e.getMessage());
        repo.save(errorToInsert); // aici scriu intr-o tranzactie deja moarta (zombie)
    }
}