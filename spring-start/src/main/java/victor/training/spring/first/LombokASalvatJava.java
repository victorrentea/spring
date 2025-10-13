package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import victor.training.spring.first.com.Z;

@Slf4j // ❤️
@Component
@RequiredArgsConstructor // ❤️ genereaza in .class constructor pt toate camp finale
public class LombokASalvatJava {
//  private static final Logger log = LoggerFactory.getLogger(LombokASalvatJava.class);
  private final X x;
  private final Y y;
  private final Z z;

  public void method() {
    log.info("Salut!");
  }
}
