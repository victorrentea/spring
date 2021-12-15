package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo messageRepo;
    private final EntityManager entityManager;
    private final JdbcTemplate jdbcTemplate;
    private final AnotherClass other;
    private final MyBatisMapper mybatis;

//    @Transactional
//    public void method() {
//        // pt migrari de proiecte la spring care foloseau direct sql.Connection
//        // antipattern petru proj noi
//        DataSource ds;
////        Connection conn = ds.getConnection();
//        Connection connection = DataSourceUtils.getConnection(ds);
//        connection.setAutoCommit(true);
//        connection.prepareStatement("INSERT INTO ...").executeUpdate();
//        connection.prepareStatement("INSERT INTO ...").executeUpdate();
////        connection.commit();
//    }

    @Transactional
    public void transactionOne() {
        jdbcTemplate.update("insert into MESSAGE(id, message) values ( 100,? )", "ALO");
        entityManager.persist(new Message("JPA"));
        messageRepo.save(new Message(null));
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