package victor.training.spring.transactions;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BlobInputStreamTypeHandler;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import victor.training.spring.ThreadUtils;

import java.io.File;
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


    @Transactional
    public void transactionTwo() throws IOException {
        mapper.insert(new Message(19L,"Without TX"));

        // When uploading large blob or CLOB in database, DO NOT keep these in memory as String or byte[]
        // is dangerous: what if the user upload a 1GB file tomorrow? --> Out Of Memory

        deep();
    }

    private void deep() throws IOException {
        // store the upload in a temp file.
        File file = File.createTempFile("data", ".dat");
        System.out.println("I want to make sure I clean this file at the end of Tx ");
        publisher.publishEvent(     new CleanUpFileEvent(file));
    }
    private final ApplicationEventPublisher publisher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void handleCleanUp(CleanUpFileEvent event) {
        System.out.println("Cleanin up after tx complettion: " + event.getFileToDeleteAfterTransaction());
    }
}


@Data
class CleanUpFileEvent {
    private final File fileToDeleteAfterTransaction;
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


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveError(Exception e) {
//        new RuntimeException().printStackTrace();
        Message message = new Message();
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