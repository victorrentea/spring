package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;
    private final OtherClass otherClass;

    @Transactional
    public void transactionOne() {
        repo.save(new Message("jpa1"));
        otherClass.shouldPersistSomethingNoMatterIfTheCallerTransactionCommitedOrNot();
        repo.save(new Message(null));

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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void shouldPersistSomethingNoMatterIfTheCallerTransactionCommitedOrNot() {
        repo.save(new Message("ME! error reporting, progress of your job"));
    }
}