package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class Playground {
    private final EntityManager entityManager;
    private final MessageRepo repo;

    private final AnotherClass other;

    @Transactional
    public void transactionOne() throws IOException {
        repo.save(new Message("jpa"));
        try {
            riskyStep();
        } catch (Exception e) {
            repo.save(new Message("EROARE: " + e.getMessage()));
        }
    }

    private void riskyStep() throws IOException {
        throw new RuntimeException("");
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
}