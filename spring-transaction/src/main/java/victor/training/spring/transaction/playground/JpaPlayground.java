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
    repo.save(new Message("JPA"));
  }

  public void transactionTwo() {}
}