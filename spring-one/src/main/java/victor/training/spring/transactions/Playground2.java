package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@RequiredArgsConstructor
@Service
public class Playground2 {
   private final MessageRepo repo;
private final AltaClasa altaClasa;
   private TransactionTemplate newTx;
   @Autowired
   public void initTxTemplate(PlatformTransactionManager transactionManager) {
      newTx = new TransactionTemplate(transactionManager);
      newTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
   }

//   @Autowired
//   TransactionTemplate newTx;

   @Transactional
   public void method() {
      System.out.println("E oare proxy: " + altaClasa.getClass());
      repo.save(new Message("Met1"));
      try {
         altaClasa.method();
      } catch (Exception e) {
         // tx e moarta aici (zombie)
         System.err.println(e.getMessage());
//         altaClasa.saveError(e);
//         saveError(e);
         newTx.executeWithoutResult(s -> repo.save(new Message("EROARE: " + e.getMessage())));
      }
      repo.save(new Message("Met1 dupa"));


      Page<Message> page = repo.findAllByMessageContaining("et", PageRequest.of(0, 2, Direction.DESC, "message"));
      System.out.println(page);
      System.out.println(repo.findAll());
      System.out.println("Oare ajung aici ");
   }
   // SELECT .... LIMIT 4 OFFET 2

}
@Service
   @Transactional // cheama proxy
@Retention(RetentionPolicy.RUNTIME)
@interface  Facade {
}

@Facade
@RequiredArgsConstructor
class AltaClasa {
   private final MessageRepo repo;

   // adnotarea (@Transactional, @Cacheable, @Timed..., @Aspect) este o CERERE de proxy!
   // oriunde Spring va injecta AltaClasa, va da Proxy, nu instanta reala

   @Transactional(propagation = Propagation.REQUIRES_NEW) // NU MERGE!
   // am vrut sa deschid tranzactie noua, dar nu a mers pentru ca a fost apel local. NU ai trecut prin nici un proxy injectat de spring
   public void saveError(Exception e) {
      repo.save(new Message("EROARE: " + e.getMessage()));
   }
   public void method() throws IOException {
      repo.save(new Message("Met2"));
//      throw new IllegalArgumentException("Exception");
      if (true) throw new IOException("Exception"); // cand proxyul de @Trasactional vede ca arunci checked exception (nu Runtime), NU marcheaza Tx curenta ca ROLLBACK_ONLY
      repo.save(new Message("Met2"));
   }
   // **concluzia**: nu aruncati vreodata ex checked din @Transactional (sau de oriunde)

}