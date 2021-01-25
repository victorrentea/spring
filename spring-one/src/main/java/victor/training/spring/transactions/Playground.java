package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class Playground {
    private final SimpleExamplesMapper mapper;
    private final AnotherClass another;

    @Transactional
    public void transactionOne() {
        mapper.insert(new Message(1L, "ONE"));

        Message message = mapper.select(1);
        System.out.println(message);

        try {
            another.insert();
        } catch (Exception e) {
            //shaworma
            e.printStackTrace();
        }
        mapper.insert(new Message(9L, "NINE"));

    }

    @Transactional
    public void transactionTwo() {
    }
}

@Service
@RequiredArgsConstructor
class AnotherClass {
    private final  SimpleExamplesMapper mapper;

    @Transactional//(propagation = Propagation.REQUIRES_NEW)
    public void insert() {
        mapper.insert(new Message(null, "ONE"));
    }
}