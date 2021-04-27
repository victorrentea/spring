package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;

@Slf4j
@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;
    private final EntityManager em;
    private final JdbcTemplate jdbc;
    private final TransactionTemplate newTx;

    @Transactional
    public void transactionOne() {
        jdbc.update("insert into MESSAGE(id, message) values ( 100,'ALO' )");
    }


    public void untransacted() {
//        localMethod(); // ERROR
//        myselfProxied.localMethod(); // WORKS
//        newTx.execute(status -> {localMethod();return null;}); // WORKS
        ((Playground)AopContext.currentProxy()).localMethod(); // WORKS
    }

    @Transactional
    public void localMethod() {
        Message message = repo.findById(100L).get();
        System.out.println(message.getTags());

    }

    @Autowired
    private Playground myselfProxied;


}