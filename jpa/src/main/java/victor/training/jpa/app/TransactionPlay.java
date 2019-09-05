package victor.training.jpa.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.ErrorLog;

import javax.persistence.EntityManager;

@Component
public class TransactionPlay {
    @Autowired
    private EntityManager em;
    @Autowired
    AltServiciu altServiciu;

    @Transactional
    public void transactedMethod() throws Exception {
        em.persist(new ErrorLog("Kernel Panic"));
        try {
            altServiciu.m();
        } catch (Exception e) {
            // :) shaworma
        }
    }
}

@Component
class AltServiciu {
    @Autowired
    private EntityManager em;
//    @Transactional(propagation = Propagation.MANDATORY)
    public void m() {
        em.persist(new ErrorLog("Kernel Panic #2"));
        throw new RuntimeException(":)");

    }

}