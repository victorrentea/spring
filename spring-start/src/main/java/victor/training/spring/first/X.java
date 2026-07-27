package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class X {
  private final Y y;
  private final Z z;
  private final List<MailService> toate; // "filtre prin care treci pe rand"

  public int logic() {
    return 1 + y.logic();
  }
}
