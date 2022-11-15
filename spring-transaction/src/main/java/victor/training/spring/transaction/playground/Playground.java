package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;
    private final OtherClass otherClass;

    @Transactional
    public void transactionOne(String someParam) {
        repo.save(new Message("jpa1"));
        otherClass.shouldPersistSomethingNoMatterIfTheCallerTransactionCommitedOrNot();
        repo.save(new Message("valid"));


        if (someParam.toUpperCase().equals("A")) {
            log.info("Stuff");
            throw new IllegalArgumentException();
        }

        // 1 Cause a rollback by breaking NOT NULL, throw Runtime, throw CHECKED
        // 2 Tx propagates with your calls (in your thread😱)        OK
        // 3 Difference with/out @Transactional on f() called: zombie transactions; mind local calls⚠️
        // 4 Game: persist error from within zombie transaction: REQUIRES_NEW or NOT_SUPPORTED
        // 5 Performance: connection starvation issues : debate: avoid nested transactions
    }

    @Transactional
    public void transactionTwo() {
    }
}

@Service
@RequiredArgsConstructor
class OtherClass {
    private final MessageRepo repo;
    @Async
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void shouldPersistSomethingNoMatterIfTheCallerTransactionCommitedOrNot() {
        repo.save(new Message("ME! error reporting, progress of your job"));
    }
}