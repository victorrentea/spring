package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class PlaygroundJpa {
  private final MessageRepo repo;
  private Long id;

  @Transactional
  public void transactionOne() {
    id = repo.save(new Message("JPA")).getId();
  }

  @Transactional
  public void transactionTwo() {
    Message message = repo.findById(id).orElseThrow();
    message.setMessage("Altu");
    // JPA face automat flush la modificarile pe
    // @Entity luate din JPA la final de tx daca erai in @Transaction
    // => buguri cand din neatentie 'carpesti' un camp intr-o entity
    // si ramane carpit in DB UPDATE =
  }
}
