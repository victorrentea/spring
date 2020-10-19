package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class Playground {
    @Autowired
    private final AnotherClass other;

    @Autowired
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    @Transactional
    public void transactionOne() throws IOException {
        try {
//        getSession().save(new Message("asdsadsadkasjdksajdksajdksajdksajdksajdksajdksajdksadjksadksajdksajdksajdsadksajdasdsadsadkasjdksajdksajdksajdksajdksajdksajdksajdksadjksadksajdksajdksajdsadksajdasdsadsadkasjdksajdksajdksajdksajdksajdksajdksajdksadjksadksajdksajdksajdsadksajdasdsadsadkasjdksajdksajdksajdksajdksajdksajdksajdksadjksadksajdksajdksajdsadksajdasdsadsadkasjdksajdksajdksajdksajdksajdksajdksajdksadjksadksajdksajdksajdsadksajdasdsadsadkasjdksajdksajdksajdksajdksajdksajdksajdksadjksadksajdksajdksajdsadksajd"));
            boolean naspa = true;
            getSession().save(new Message("JOB START"));
           other.oMetodaChemataCandva(naspa);
            getSession().save(new Message("JOB DONE"));
        } catch (Exception e) {
            other.persistFailureInDB(e.getMessage());
            //throw e; NIMIC! VideoChat scrie pe tine
        }
    }
    @Transactional
    public void transactionTwo() {
        // TODO Repo API
        // TODO @NonNullApi
    }
}
@Service
@RequiredArgsConstructor
class AnotherClass {
    private final MessageRepo repo;


    @Transactional
   public void oMetodaChemataCandva(boolean naspa) {
        if (naspa) throw new RuntimeException();
    }

    @Autowired
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

//    PlatformTransactionManager t;
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void persistFailureInDB(String messageStr) {
//        if (!t.getTransaction().isRollbackOnly()) {
//            run in new Tx
//        } else {
//            run in this TX
//        }
        Message message = new Message("inserata din alta clasa: " + messageStr);
        getSession().save(message);
        System.out.println("Is the current tx dead ?" + getSession().getTransaction().getRollbackOnly());
        System.out.println(message.getId());
    }
}