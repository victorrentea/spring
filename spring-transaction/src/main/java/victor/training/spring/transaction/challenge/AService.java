package victor.training.spring.transaction.challenge;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

@Slf4j
@Service
public class AService {
   @Autowired
   private BService b;
   @Autowired
   private Repo repo;

   public void a1() {
      log.info("a1");
      b.b1();
   }

   @Transactional
   public void a2() {
      log.info("a2");
      b.b1();
   }

   @Transactional
   public void a3() {
      log.info("a3");
      b.b1();
   }

   @Transactional
   public void a4() {
      log.info("a4");
      b.b2();
   }

   @Transactional
   public void a5() {
      log.info("a5");
      b.b3();
   }

   @Transactional(propagation = NOT_SUPPORTED)
   public void a6() {
      log.info("a6");
      b.b3();
   }

}
