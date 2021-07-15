package victor.training.spring.transactions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Slf4j
@RestController
class Controller {

   @EventListener
   public void onStartupCallAll(ApplicationReadyEvent event) {
      RestTemplate rest = new RestTemplate();
      for (int i = 1; i <= 6; i++) {
         System.out.println("================ FLOW #" + i + " ==================");
         rest.getForObject("http://localhost:8080/"+i, Void.class);
      }

      // TODO add this to application.properties:
      // logging.level.org.springframework.orm.jpa.JpaTransactionManager=TRACE
   }

   @Autowired
   private AService a;

   @GetMapping("1")
   public void c1() {
      a.a1();
   }
   @GetMapping("2")
   public void c2() {
      a.a2();
   }
   @GetMapping("3")
   public void c3() {
      a.a3();
   }
   @GetMapping("4")
   public void c4() {
      a.a4();
   }
   @GetMapping("5")
   public void c5() {
      a.a5();
   }
   @GetMapping("6")
   public void c6() {
      a.a6();
   }
}
@Slf4j
@Service
class AService {
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
@Slf4j
@Service
@Transactional
class BService {
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
@Slf4j
@Repository
@Transactional
class Repo {
   public void r1() {
      log.info("r1");
   }
}