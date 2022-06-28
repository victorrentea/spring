package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
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

        OtherClass.var.set("a");
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
    public static final ThreadLocal<String> var = new ThreadLocal<>(); //
    // ceva in genul asta merge connectionul de JDBC intre metodele invocate intr-un flux.

    public void m() {
        System.out.println("Date private ale threadului meu " + var.get());
        repo.save(new Message("tranzactia"));
        repo.save(new Message("count.amount -- "));
        repo.save(new Message(null));
    }
}