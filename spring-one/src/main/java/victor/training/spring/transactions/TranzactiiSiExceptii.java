package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor // constructor generat doar in bytecode (invizibil in cod)
public class TranzactiiSiExceptii {
   private final MessageRepo repo;
   private final Alta alta;

   @Transactional
   public void method() throws IOException {
      repo.save(new Message("PRIMUL"));
//      throw new IllegalArgumentException(); // runtime ex distruge tranzactia curenta
//      throw new IOException(); // runtime ex distruge tranzactia curenta
      try {
         alta.altaMetoda();
      } catch (Exception e) {
         // va rog eu mult. Nu faceti NICIODATA ASA CEVA: 
         e.printStackTrace(); // inghit exceptia
         // Shaworma-driven error handling
//         throw new RuntimeException(e);
      }
   }

}

@Component
@RequiredArgsConstructor
class Alta {
   private final MessageRepo repo;

   @Transactional(propagation = Propagation.REQUIRES_NEW)
   public void altaMetoda() throws IOException {
      repo.save(new Message("A DOUA"));
      throw new IllegalArgumentException();
   }
}




