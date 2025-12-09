package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Component
public class PlayDualWrite {
    private final MyEntityRepo repo;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void saveAndSend() {
        repo.save(new MyEntity("E12"));
        repo.save(new MyEntity("E22"));
        repo.flush();
        //send(new MyMessage("M")); // ❌ => ex => rollback
        applicationEventPublisher.publishEvent(new ProcessPayment("M"));
    }
    // ruleaza dupa COMMITul tx din care s-a facut .publishEvent
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void afterCommit(ProcessPayment message) {
        try {
            send(message); // timeout dpa 100m
        } catch (Exception e) {
            // k8s kill/redeploy/OOME💥
            // COMPENSEZI cu o a 2a tranzactie care da pa dos pa prima comisa deja
            // "DILESTI CE INSERASHI"
        }
    }
// INSERT + @NotNull @Size
// INSERT
// COMMIT ❌ FK/PK/UK💥
// SEND KAFKA/RABBIT/PUB-SUB ✅ nu *prea* pica=> il faci ultimul

    public record ProcessPayment(String content) {
    }

    private void send(ProcessPayment message) {
        log.info("kafkaTemplate.send(" + message + ") pretend");
        log.info("restTemplate.post(" + message + ") pretend API CALL");
//    if (Math.random() < .5) throw new RuntimeException("BOOM"); // may fail
    }
}
