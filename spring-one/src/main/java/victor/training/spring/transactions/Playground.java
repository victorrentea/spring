package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class Playground {
    private final MessageRepo repo;
    private final AnotherClass other;
//    TransactionTemplate txTemplate;

    @Autowired
    public void setupTxTemplate(PlatformTransactionManager transactionManager) {
//        txTemplate = new TransactionTemplate(transactionManager);
//        txTemplate.execute(status -> {
//            em.
//            return 1;
//        });
    }

    @Transactional
    public void transactionOne() {
        repo.save(new Message("initial"));
        System.out.println(repo.count());
        log.debug("Final metoda");
//        if (true) {
//            throw new IllegalArgumentException();
//        }
    }
    @Transactional
    public void transactionTwo() {
        Message message = repo.findById(1L).get();
        Message message2 = other.method();
        Message message3 = repo.metodaTare();

        System.out.println(message == message2);
        System.out.println(message == message3);
        message.setMessage("changed");
        try {
            other.riscanta();
        } catch (Exception e) {
            e.printStackTrace(); // shawarma
            other.insertError(e);
        }
        log.debug("Dupa");
        // TODO Repo API
        // TODO @NonNullApi
    }
}
@Service
@RequiredArgsConstructor
class AnotherClass {
    private final MessageRepo repo;
    @Transactional
    public Message method() {
        return repo.findById(1L).get();
        // daca ceri din nou aceeasi entitate dupa ID din baza din aceeasi tranzactie,
        // JPA iti va da ACEEASI instanta pe care o avea deja in mem (fara sa faca un nou query)
    }
    @Transactional
    public void riscanta() {
        // language=sql
        String sql = "SELECT * FROM COURSE LEFT JOIN TEACHER T on COURSE.TEACHER_ID = T.ID";
//        // language=jpql
//        String jpql = "SELECT t FROM TEACHER t WHERE t."
        repo.findById(1L).get().setMessage("inainte de crapare");
        throw new RuntimeException();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertError(Exception e) {
        repo.save(new Message("Eroare frate " + e.getMessage()));
    }
}