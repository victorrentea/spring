package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class Playground {
    private final JdbcTemplate jdbc;
    private final  SimpleExamplesMapper mapper;

    @Transactional
    public void transactionOne() {
//        jdbc.update("INSERT INTO MESSAGE(ID, MESSAGE) VALUES" +
//                    " ( 1 , ? )",
//            "ONE");

        mapper.insert(new Message(1L, "ONE"));

        Message message = mapper.select(1);
        System.out.println(message);
//        jdbc.update("INSERT INTO MESSAGE( MESSAGE) VALUES" +
//                    " (  ? )",
//            "TWO");
    }
    @Transactional
    public void transactionTwo() {
    }
}


// iBatis/ myBatis is just an Mapper Entity - Tables
// Hibernate is so much more than that.


@Service
@RequiredArgsConstructor
class AnotherClass {
    private final MessageRepo repo;
}