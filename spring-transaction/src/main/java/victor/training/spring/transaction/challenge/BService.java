package victor.training.spring.transaction.challenge;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Slf4j
@Service
@Transactional
public class BService {
   @Autowired
   private Repo repo;

   public void b1() {
      log.info("b1");
      repo.r1();
   }

   @Transactional(propagation = REQUIRES_NEW)
   public void b2() {
      log.info("b2");
      repo.r1();
   }

   @Transactional(propagation = NOT_SUPPORTED)
   public void b3() {
      log.info("b3");
      repo.r1();
   }

}
