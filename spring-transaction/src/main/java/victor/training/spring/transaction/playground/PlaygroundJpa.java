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
    repo.save(new Message("#sieu"));
    // inserturile (orice scriere in DB) JPA o "amana"
    // pana la COMMIT = "write behind"
    // 1) poate da rollback si nu le mai face (pesismist)
    // 2) ca sa faca batching (sa reduca round-tripurile catre DB)
    System.out.println("Si acum trimit Rabbit");
  }

  @Transactional(readOnly = true)
  public void transactionTwo() {
    Message message = repo.findById(id).orElseThrow();
    message.setMessage("Altu");

    System.out.println("Iar: " +repo.findById(id).orElseThrow());
    // iti serveste entitate din memorie (din 1ast leve caching)
    System.out.println("Iar: " +repo.findById(id).orElseThrow());
    System.out.println("Iar: " +repo.findById(id).orElseThrow());
    System.out.println("Iar: " +repo.findById(id).orElseThrow());
    System.out.println("Iar: " +repo.findById(id).orElseThrow());
    System.out.println("Iar: " +repo.findById(id).orElseThrow());

  }
//
//  @Transactional
//  public void method() {
//    var t = trainRepo.findbyid(id);
//    t.setStatus(altu); // UPDATE
//    auditRepo.save(new Audit(t.id, newStatus)) // INSERT
  //  trainRepo.save(t);
//  }
}
