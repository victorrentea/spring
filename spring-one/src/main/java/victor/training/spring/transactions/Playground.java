package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;
    private final EntityManager em;
    private final AnotherClass other;

    @Transactional
    public void transactionOne() {
        repo.save(new Message("Ok"));
        try {
            other.bigNastyLogic();
        } catch (Exception e) {
            repo.save(new Message("ERROR: " + e));
        }
        System.out.println("End of method. The inserts happen AFTER this line.");
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

    public void bigNastyLogic() {
        throw new RuntimeException("BUM!");
    }
}