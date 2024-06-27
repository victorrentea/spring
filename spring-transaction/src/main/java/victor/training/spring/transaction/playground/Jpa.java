package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class Jpa {
  private final MessageRepo repo;
  private Long id;

  @Transactional
  public void one() {
//    Message m = new Message("ONE");
//    repo.save(m);
//    id = m.getId(); is same as:
    id = repo.save(new Message("ONE")).getId();
    iaopasta();
//    repo.findAll().forEach(System.out::println); // cauzeaza autoflush prematur
    h();
//    repo.flush(); // sau asa
    log.info("End of method ---");
  } // write-behind: DUPA ce ies din metoda TxInterc face FLUSH(trimite IN DB insert/update/delete) + COMMIT

  private void iaopasta() {
    repo.saveAndFlush(new Message("TWObis")); // forteaza FLUSH dupa insert.
  }

  private void h() {
    g();
  }

  private void g() {
    f();
  }

  private void f() {
    repo.saveAndFlush(new Message("TWO"));
  }


  public void two() {
    Message e = repo.findById(1L).orElseThrow();
    e.setMessage("schimbare"); // nu se duce in DB ca e este 'detasata'
  }
}
