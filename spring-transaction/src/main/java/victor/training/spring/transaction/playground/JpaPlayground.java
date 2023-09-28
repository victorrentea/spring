package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class JpaPlayground {
  private final MessageRepo repo;

  @Transactional
  public void transactionOne() throws IOException {
    Message mess = new Message("JPA");
    repo.save(mess);
    // . JPA iti seteaza IDul la save()

    System.out.println("Send Rabbit. id:" + mess.getId());
  }

  public void transactionTwo() {}
}