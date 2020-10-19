package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.persistence.EntityManager;
import java.io.IOException;


class DeleteTemporaryFiles {

}
class NotifyTransactionCompleted {
   private final String email;

   NotifyTransactionCompleted(String email) {
      this.email = email;
   }

   public String getEmail() {
      return email;
   }
}

@Service
@RequiredArgsConstructor
public class Playground {
   private static final Logger log = LoggerFactory.getLogger(Playground.class);
    @Autowired
    private final AnotherClass other;

    @Autowired
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    @Autowired
    private ApplicationEventPublisher publisher;

   @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
   public void notifyTxOK(NotifyTransactionCompleted notifyTransactionCompleted) {
      log.info("Notifying success to : " + notifyTransactionCompleted.getEmail());
   }
   @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
   public void deleteTempFiles(DeleteTemporaryFiles event) {
      log.info("Delete temp files : ");
   }

   @Transactional
   public void transactionOne() throws IOException {
      try {
//        getSession().save(new Message("asdsadsadkasjdksajdksajdksajdksajdksajdksajdksajdksadjksadksajdksajdksajdsadksajdasdsadsadkasjdksajdksajdksajdksajdksajdksajdksajdksadjksadksajdksajdksajdsadksajdasdsadsadkasjdksajdksajdksajdksajdksajdksajdksajdksadjksadksajdksajdksajdsadksajdasdsadsadkasjdksajdksajdksajdksajdksajdksajdksajdksadjksadksajdksajdksajdsadksajdasdsadsadkasjdksajdksajdksajdksajdksajdksajdksajdksadjksadksajdksajdksajdsadksajdasdsadsadkasjdksajdksajdksajdksajdksajdksajdksajdksadjksadksajdksajdksajdsadksajd"));
         boolean naspa = true;
         publisher.publishEvent(new NotifyTransactionCompleted("a@b.com"));
         publisher.publishEvent(new DeleteTemporaryFiles(/*"file.getPath"*/));
         log.info("Aici incep de fapt treaba");
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
   private static final Logger log = LoggerFactory.getLogger(AnotherClass.class);
    private final MessageRepo repo;


    @Transactional
   public void oMetodaChemataCandva(boolean naspa) {
        if (false) throw new RuntimeException();
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
        log.info("Is the current tx dead ?" + getSession().getTransaction().getRollbackOnly());
        log.info(message.getId() + " id");
    }
}