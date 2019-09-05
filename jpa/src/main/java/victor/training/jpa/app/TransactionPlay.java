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

    @Transactional(rollbackFor = Exception.class)
    public void transactedMethod() throws Exception {
        em.persist(new ErrorLog("Kernel Panic"));
        throw new Exception(":)");
    }

}
