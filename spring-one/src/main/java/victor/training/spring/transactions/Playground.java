package victor.training.spring.transactions;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.persistence.*;
import java.io.IOException;
import java.util.List;

interface ParentRepo extends JpaRepository<Parent, Long> {
}

@Entity
@Data
class Parent {
    @Id
    @GeneratedValue
    private Long id;
    @OneToMany
    private List<Child> children;
}

@Entity
@Data
class Child {
    @Id
    @GeneratedValue
    Long id;
}

@Slf4j
@Service
@RequiredArgsConstructor
public class Playground {
    private final JdbcTemplate jdbc;
    private final EntityManager em;
    private final MessageRepo repo; // Spring Data JPA

    private final AnotherClass other;
    private final ParentRepo parentRepo;

    @Transactional
    public void transactionOne() {
        jdbc.update("insert into MESSAGE(id, message) values ( 100,'ALO' )");
        repo.save(new Message("null"));
//        em.persist(new Message(null));

//        try {
//            other.altaMetodaTranzactata();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        System.out.println(repo.findByMessage("JPA")); // causes a flush() pentru ca ceea ce avea de pus in baza ar putea influenta rez queryului.
        log.debug("Ies din metoda");
        parentRepo.save(new Parent());
        parentRepo.save(new Parent());
        parentRepo.save(new Parent());
        parentRepo.save(new Parent());
        parentRepo.save(new Parent());
        parentRepo.save(new Parent());

        other.adanc();
    }


    @Transactional(readOnly = true)
    public void transactionTwo() {

        Message message = repo.findById(100L).get();
        message.setMessage("alt mesaj");
//        repo.save(message);

        for (Parent parent : parentRepo.findAll()) {
            System.out.println("Parent children: " + parent.getChildren());
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public void transactionThree(String mesajDePeCoada) throws IOException {
        Message e1 = repo.findById(100L).get();
        Message e2 = other.dinAltaParte();

        System.out.println("a1" == "a" + 1);

        System.out.println("Cum Doamne iarta-ma sunt astea == " + (e1==e2));

        try {
            Boolean eroare = true;
            repo.save(new Message("Am primit: " + mesajDePeCoada));
            if (eroare) {
                throw new IOException("NASPA FRATE");
            }
        } catch (Exception e) {
//            log.error(e.getMessage(), e);
            other.persistErorrInDifferentTx(e);
            throw e;
        }
        em.persist(new Message("In veci in DB"));
    }

}

@Service
@RequiredArgsConstructor // generates constructor for all final fields, used by Spring to inject dependencies
class AnotherClass {

    private final MessageRepo repo;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void persistErorrInDifferentTx(Exception e) {
        repo.save(new Message("EROARE: " + e.getMessage()));
    }

    @Transactional
    public void adanc() {
        publisher.publishEvent(new AfterTransactionEvent("fis de sters"));
    }
    @Autowired
    ApplicationEventPublisher publisher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void cleanupAfterThread(AfterTransactionEvent event) {
        System.out.println("ACUM STERG " + event);
    }

    public Message dinAltaParte() {
        Message e2 = repo.findById(100L).get();
        return e2;
    }

    class AfterTransactionEvent {
        private final String fisiereDeSters;

        public AfterTransactionEvent(String fisiereDeSters) {
            this.fisiereDeSters = fisiereDeSters;
        }

        @Override
        public String toString() {
            return "AfterTransactionEvent{" +
                   "fisiereDeSters='" + fisiereDeSters + '\'' +
                   '}';
        }
    }

    @Transactional
    public void altaMetodaTranzactata() {
        repo.save(new Message("Inca una mai flacai!"));
        throw new RuntimeException("Intentionata");
    }
}