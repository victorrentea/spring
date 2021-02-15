package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class Playground {
    private final MessageRepo repo;
    private final EntityManager em;
    private final AnotherClass other;
    private TransactionTemplate txTemplate;

    @Transactional
    public void transactionOne() {
        repo.save(new Message("jpa")); // are @Transactional pe ea
        System.out.println(repo.findByMessageLike("%p%"));
        System.out.println(repo.finduMeu("%P%"));
//        String bum = StringUtils.repeat("a", 256);
//        repo.save(new Message(bum));
        System.out.println("Gata, ies din metoda. Ma duc la proxy...");
    }

    @Transactional
    public void transactionTwo() {
        Message message = repo.findById(1L).get();

        message.setMessage("Nou mesaj");

        Message m2 = other.surprizaCaIauDinAcelasiCache();

        // TODO Repo API
        // TODO @NonNullApi
        System.out.println(message == m2);

        try {
            other.bum();
        } catch (Exception e) {
            this.persistError(e.getMessage()); // nici un proxy nu poate interveni pe un apel local in aceeasi clasa
        }
    }

//    @Transactional(propagation = Propagation.REQUIRES_NEW) //<<<<<<<<<<<
    public void persistError(String errorMessage) {
        txTemplate.execute(status -> {
            repo.save(new Message("EROARE: " + errorMessage));
            return null;
        });
    }

    	@Autowired
	public void initTxTemplate(PlatformTransactionManager transactionManager) {
		this.txTemplate = new TransactionTemplate(transactionManager);
		txTemplate.setPropagationBehavior(Propagation.REQUIRES_NEW.value());
		txTemplate.setTimeout(5);
	}
}
@Service
@RequiredArgsConstructor // generates constructor for all final fields, used by Spring to inject dependencies
class AnotherClass {
    private final MessageRepo repo;
    @Transactional/// <<<<<<<<
    public void bum() throws IOException {
        boolean inProd = true;
        if (inProd){
            throw new NullPointerException(); // execeptie RUNTIME care trece prin proxy de Transacational distruge tranzactia
        }
//        throw new IOException("File nu e"); // nu o distruge!?!!!!!! DE CE ?!!
        // NU folosi exceptii checked. DE LOC. SUNT RElE> vechi. si o dovada ca Java e un lb din lume
        //
    }


    public Message surprizaCaIauDinAcelasiCache() {
        return repo.findById(1L).get();
    }
}