package victor.training.spring.transaction.propagation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

// === EXEMPLUL 3: NOT_SUPPORTED — o operație lentă nu trebuie să țină conexiunea DB ocupată ===
//
// Scenariu real: generezi un raport PDF mare sau trimiți un email.
// Dacă ești apelat din interiorul unei tranzacții, NOT_SUPPORTED o SUSPENDĂ temporar.
// Astfel, conexiunea la DB e eliberată pe durata operației lente.
// Fără asta, conexiunea stă ocupată (blocând alte request-uri) pe toată durata generării raportului.

@Service
public class ReportService {

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    // Suspendă orice tranzacție activă pe thread-ul curent cât timp rulează această metodă.
    public boolean isTransactionActive() {
        return TransactionSynchronizationManager.isActualTransactionActive();
    }
}
