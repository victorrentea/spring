package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.ThreadUtils;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class Playground {
    private final MyBatisMapper mapper;
    private final AnotherClass another;
    private final NoTransactionalInBetween noTx;

    @Transactional
    public void transactionOne() {
        System.out.println("To who am I talking ? " + another.getClass());
        mapper.insert(new Message(6l, "First"));
        try {
            another.riskyOperation();
        } catch (Exception e) {
            another.saveError(e);
        }
        noTx.m();
    }
    //    @Transactional
    public void transactionTwo() {
        mapper.insert(new Message(19L,"Without TX"));
    }
}

@Service
@RequiredArgsConstructor
class NoTransactionalInBetween {
    private final MyBatisMapper mapper;
    @Async
    public void m() {
        ThreadUtils.sleep(1000L);
        mapper.insert(new Message(99L, "aa"));
    }
}

@Service
@RequiredArgsConstructor
class AnotherClass {
    private final MyBatisMapper mapper;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void saveError(Exception e) {
//        new RuntimeException().printStackTrace();
        mapper.insert(new Message(9L, "ERROR " + e.getMessage()));
        ThreadUtils.sleep(1000L);
    }


    @Transactional(rollbackFor = Exception.class)//(propagation = Propagation.REQUIRES_NEW)
    public void riskyOperation() throws IOException {
//        mapper.insert(new Message(null, "ONE"));

//        throw new NullPointerException();
        throw new IOException("File not founrd :bla bla"); // from my tests , this allows ERROR to be INSERTED
    }
}