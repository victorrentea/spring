package victor.training.spring.transaction.propagation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// === EXEMPLUL 2: REQUIRES_NEW — import batch: salvează ce poți, sari peste ce eșuează ===
//
// Scenariu real: importi 1000 de plăți dintr-un fișier CSV.
// Dacă una e duplicat (unique constraint), vrei să continui cu restul, nu să anulezi tot.
// Cu REQUIRES_NEW, fiecare salvare e o tranzacție separată; excepția nu contaminează celelalte.

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentImporter {
    private final SinglePaymentSaver singlePaymentSaver;

    // Fără @Transactional — nu există o tranzacție "parinte" care să encompaseze tot
    public void importAll(List<String> references) {
        for (String ref : references) {
            try {
                singlePaymentSaver.save(ref);
            } catch (Exception e) {
                log.warn("Skipped '{}': {}", ref, e.getMessage());
            }
        }
    }
}

@Service
@RequiredArgsConstructor
class SinglePaymentSaver {
    private final PaymentRepo paymentRepo;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    // Fiecare apel = tranzacție proprie. Dacă dă excepție → rollback doar pe ea.
    public void save(String reference) {
        paymentRepo.save(new Payment(reference));
    }
}
