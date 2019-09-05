package victor.training.jpa.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.ErrorLog;

import javax.persistence.EntityManager;

@Component
public class TransactionPlay {
    @Autowired
    private EntityManager em;
    @Autowired
    AltServiciu altServiciu;

    @Transactional (noRollbackFor = RuntimeException.class)
    public void transactedMethod() throws Exception {
        em.persist(new ErrorLog("Kernel Panic"));
        try {
            altServiciu.m();
        } catch (Exception e) {
            altServiciu.persistError(e.getMessage());
        }
        em.persist(new ErrorLog("Kernel Panic444"));
    }

}
@Component
class AltServiciu {
    @Autowired
    private EntityManager em;
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void persistError(String message) {
        em.persist(new ErrorLog("ERoare pe bune :"  + message));
    }
//    @Transactional(propagation = Propagation.MANDATORY)
    @Transactional(noRollbackFor = RuntimeException.class)
    public void m() {
        em.persist(new ErrorLog("Kernel Panic #2"));
//        new RuntimeException().printStackTrace();
        throw new RuntimeException(":)");

    }

}