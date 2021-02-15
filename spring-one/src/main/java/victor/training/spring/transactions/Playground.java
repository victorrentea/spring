package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;
    private final EntityManager em;

    @Transactional
    public void transactionOne() {
        repo.save(new Message("jpa"));
        System.out.println(repo.findByMessageLike("P"));
        System.out.println(repo.finduMeu("P"));
        String bum = StringUtils.repeat("a", 256);
        repo.save(new Message(bum));
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