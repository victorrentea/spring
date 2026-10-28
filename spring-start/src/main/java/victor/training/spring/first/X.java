package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor // ce face asta
@Service
public class X {
  private final Y y;
  private final Z z;

  public int logic() {
    return 1 + y.logic();
  }
}
