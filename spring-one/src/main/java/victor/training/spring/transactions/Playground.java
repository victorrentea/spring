package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Playground {
    private final AnotherClass other;

    private final MessageRepo repo;
    private final EntityManager em;
    private final MyBatisMapper mybatis;
    private final NamedParameterJdbcTemplate namedJdbc;
    private final JdbcTemplate jdbc;
    private final DataSource dataSource;

    // JDBC

    @Transactional
    public void transactionOne() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 100);
        map.put("name", "ALO");

        namedJdbc.update("insert into MESSAGE(id, message) values ( :id, :name )",map);

        final List<Message> list = jdbc.query("SELECT id, message FROM MESSAGE", (rs, rowNum) -> {
            long id = rs.getLong("id");
            String message = rs.getString("message");
            return new Message(id, message);
        });
        System.out.println(list);
//        if (true) {
//            throw new RuntimeException();
//        }

        jdbc.update("insert into MESSAGE(id, message) values ( 101,? )", "ALO");
//        mybatis.search(100);
        repo.save(new Message("jpa"));

        System.out.println("Cica s-a scris ");
        repo.flush();
        System.out.println(mybatis.search("jpa"));

        System.out.println("se termina metoda");
    }

    @Transactional
    public void transactionTwo() {

        Message message = repo.findById(100L).get();

        message.setMessage("Alt mesaj");

        // TODO Repo API
        // TODO @NonNullApi
    }
}


@Service
@RequiredArgsConstructor // generates constructor for all final fields, used by Spring to inject dependencies
class AnotherClass {
    private final MessageRepo repo;
}