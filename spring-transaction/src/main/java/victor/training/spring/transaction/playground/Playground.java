package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.sql.Connection;

@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;
    private final EntityManager entityManager;
    private final JdbcTemplate jdbc;
    private final OtherClass other;

    @Transactional
    public void transactionOne() throws IOException {
        jdbc.update("insert into MESSAGE(id, message) values ( 100,'ALO' )");
        System.out.println("😁");

        System.out.println(repo.findAllByMessage("ALO"));

        other.m();
    }

    @Transactional
    public void transactionTwo() {
    }
}

@Service
@RequiredArgsConstructor
class OtherClass {
    private final MessageRepo repo;

    public void m() throws IOException {
        repo.save(new Message("tranzactia"));
        repo.save(new Message("count.amount -- "));
        throw new IOException("BUM");
    }
}