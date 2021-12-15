package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class Playground2 {
   private final MessageRepo repo;
private final AltaClasa altaClasa;
   @Transactional
   public void method() {
      System.out.println("E oare proxy: " + altaClasa.getClass());
      repo.save(new Message("Met1"));
      try {
         altaClasa.method();
      } catch (Exception e) {
         // tx e moarta aici (zombie)
         System.err.println(e.getMessage());
         altaClasa.saveError(e);
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
   public void method() {
      repo.save(new Message("Met2"));
      throw new IllegalArgumentException("Exception");
   }
}