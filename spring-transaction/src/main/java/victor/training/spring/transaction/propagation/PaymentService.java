package victor.training.spring.transaction.propagation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

// === EXEMPLUL 1: REQUIRES_NEW — audit log salvat chiar dacă tranzacția principală dă rollback ===
//
// Scenariu real: procesezi o plată, dar vrei să loghezi tentativa INDIFERENT de rezultat.
// Fără REQUIRES_NEW, dacă plata eșuează → rollback → dispare și audit-ul!

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepo paymentRepo;
    private final AuditService auditService;

    @Transactional
    public void processPayment(String reference) {
        paymentRepo.save(new Payment(reference));
        auditService.logAttempt("Payment attempted: " + reference); // commit propriu, independent
        throw new RuntimeException("Gateway timeout"); // → rollback pe Payment, dar NU și pe audit!
    }
}

@Service
@RequiredArgsConstructor
class AuditService {
    private final AuditLogRepo auditLogRepo;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    // Suspendă tranzacția curentă, deschide una nouă, o commitează imediat la exit.
    public void logAttempt(String message) {
        auditLogRepo.save(new AuditLog(message));
    }
}
