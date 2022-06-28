package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.Connection;

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

        OtherClass.var.set("10");
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        other.m();
        // ce sta pe tjhread:
        // 1) JDBC Connection < Transaction
        // 2) PersistenceContext(JPA)
        // 3) Security Context (cine e logat, acceul la JWT token, usernameul)
        // 4) Lomback MDC
    }

    @Transactional
    public void transactionTwo() {
    }
}

@Service
@RequiredArgsConstructor
class OtherClass {
    private final MessageRepo repo;
    public static final ThreadLocal<String> var = new ThreadLocal<>(); //
    // ceva in genul asta merge connectionul de JDBC intre metodele invocate intr-un flux.

    public void m() {
        System.out.println("Date private ale threadului meu " + var.get());
        repo.save(new Message("tranzactia"));
        repo.save(new Message("count.amount -- "));
        repo.save(new Message(null));
    }
}