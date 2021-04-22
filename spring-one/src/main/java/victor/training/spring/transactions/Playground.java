package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Slf4j
@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;
    private final EntityManager em;
    private final JdbcTemplate jdbc;
    private final AnotherClass other;

    @Transactional
    public void transactionOne() {
        jdbc.update("insert into MESSAGE(id, message) values ( 100,'ALO' )");
        repo.save(new Message("jpa"));
    }


    @Transactional
    public void transactionTwo() {
        Message message = repo.findById(100L).get();
        message.setMessage("Different");
        log.debug(message.getId() + " is the id");

        repo.save(new Message("new new"));
//        repo.save(new Message(null));
//        System.out.println(repo.findByMessage("new new"));

//        repo.save(message);
//        repo.save(new Message("jpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpajpa"));

        other.methodSharingTransaction();
        System.out.println("END OF METHOD");

    }
}

class PlaygroundProxyPlay extends Playground {
    public PlaygroundProxyPlay(MessageRepo repo, EntityManager em, JdbcTemplate jdbc, AnotherClass other) {
        super(repo, em, jdbc, other);
    }

    @Override
    public void transactionTwo() {
        // start tx. put on thread.
        super.transactionTwo();
        // commit()
    }
}


@Service
@RequiredArgsConstructor // generates constructor for all final fields, used by Spring to inject dependencies
class AnotherClass {
    private final MessageRepo repo;

    @Transactional
    public void methodSharingTransaction() {
        repo.save(new Message("happy"));
    }
}