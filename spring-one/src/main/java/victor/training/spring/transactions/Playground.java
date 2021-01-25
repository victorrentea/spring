package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class Playground {
    private final SimpleExamplesMapper mapper;
    private final AnotherClass another;

    @Transactional
    public void transactionOne() {
        System.out.println("To who am I talking ? " + another.getClass());
        try {
            another.riskyOperation();
        } catch (Exception e) {
            another.saveError(e);
        }
    }


    @Transactional
    public void transactionTwo() {
    }
}
@Service
@RequiredArgsConstructor
class AnotherClass {
    private final  SimpleExamplesMapper mapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveError(Exception e) {
        new RuntimeException().printStackTrace();
        mapper.insert(new Message(9L, "ERROR " + e.getMessage()));
    }


    @Transactional(rollbackFor = Exception.class)//(propagation = Propagation.REQUIRES_NEW)
    public void riskyOperation() throws IOException {
//        mapper.insert(new Message(null, "ONE"));

//        throw new NullPointerException();
        throw new IOException("File not founrd :bla bla"); // from my tests , this allows ERROR to be INSERTED
    }
}