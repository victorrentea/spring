package victor.training.spring.transaction.playground.extra;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import victor.training.spring.transaction.playground.Message;
import victor.training.spring.transaction.playground.MessageRepo;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionListener {
    private final ApplicationEventPublisher eventPublisher;
    private final MessageRepo messageRepo;

    @Transactional
    public void insideATransaction() {
        messageRepo.save(new Message("Start"));
        eventPublisher.publishEvent(new CleanupAfterTransactionEvent("Delete files, mark rows DONE, ACK a message"));
        eventPublisher.publishEvent(new SendNotificationAfterCommitEvent("boss@corp.io", "The transaction was completed"));
        messageRepo.save(new Message("End"));
        log.info("End method");
    }

    record CleanupAfterTransactionEvent(String workToDo) {
    }

    record SendNotificationAfterCommitEvent(String email, String text) {
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void afterCompletion(CleanupAfterTransactionEvent event) {
        log.info("After completion: " + event.workToDo());
    }
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void afterCommit(SendNotificationAfterCommitEvent event) {
        log.info("Sending emails: " + event);
    }
}

