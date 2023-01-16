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

  private String currentUser; // foarte rau sa pui date specifice requestului web pe camp al singleton => race bugs
  private String stationId;

  @GetMapping
  @Transactional
  public void transactionOne(@RequestParam("user") String user) {
    currentUser = "user curent din token" + user;
    id = repo.save(new Message("JPA")).getId();
    f();

  }

  private void f() {
    System.out.println("Useru curent tinut temporar intr-un camp"
                       + currentUser);
  }

  @Transactional
  public void transactionTwo() {
  }
}
