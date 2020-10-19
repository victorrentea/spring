package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
            if (naspa) throw new RuntimeException();
            getSession().save(new Message("JOB DONE"));
        } catch (Exception e) {
            other.persistFailureInDB(e.getMessage());
            throw e;
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

    @Autowired
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void persistFailureInDB(String messageStr) {
        Message message = new Message("inserata din alta clasa: " + messageStr);
        getSession().save(message);
        System.out.println(message.getId());
    }
}