package victor.training.spring.performance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import victor.training.spring.web.entity.Message;
import victor.training.spring.web.repo.MessageRepo;

@Service
@RequiredArgsConstructor
public class TransactedService {
    private final ExpensiveApiClient apiClient;
    private final MessageRepo messageRepo;

    public void flow() {
        String data = apiClient.blockingCall();


        transactionTemplate.executeWithoutResult(s ->
                {
                    messageRepo.save(new Message(data));
                    messageRepo.save(new Message(data));
                }
        );
    }

    private TransactionTemplate transactionTemplate;

    @Autowired
    public void setTransactionTemplate(PlatformTransactionManager manager) {
        this.transactionTemplate = new TransactionTemplate(manager);
        //      transactionTemplate.setPropagationBehaviorName("REQUIRES_NEW");
    }
}
