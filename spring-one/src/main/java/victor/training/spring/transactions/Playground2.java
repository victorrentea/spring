package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.IOException;

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
      System.out.println(repo.findAll());
      System.out.println("Oare ajung aici ");
   }

}
@Service
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
   @Transactional // cheama proxy
   public void method() throws IOException {
      repo.save(new Message("Met2"));
//      throw new IllegalArgumentException("Exception");
      throw new IOException("Exception"); // cand proxyul de @Trasactional vede ca arunci checked exception (nu Runtime), NU marcheaza Tx curenta ca ROLLBACK_ONLY
   }
   // **concluzia**: nu aruncati vreodata ex checked din @Transactional (sau de oriunde)

}