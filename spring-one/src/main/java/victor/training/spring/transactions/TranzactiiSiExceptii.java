package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor // constructor generat doar in bytecode (invizibil in cod)
public class TranzactiiSiExceptii {
   private final MessageRepo repo;

   @Transactional
   public void method() throws IOException {
      repo.save(new Message("PRIMUL"));
//      throw new IllegalArgumentException(); // runtime ex distruge tranzactia curenta
      throw new IOException(); // runtime ex distruge tranzactia curenta
   }
}
