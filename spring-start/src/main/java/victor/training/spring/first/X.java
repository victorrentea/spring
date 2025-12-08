package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Slf4j
@Service // clasa devine bean = obiect instantiat, config, manipulat de Spring
@RequiredArgsConstructor // genereaza cod la javac
public class X {
// Dependency Injection = la startup, Spring cauta un bean de tip Y si il injecteaza aici
//  @Autowired // #1 field injection folosita doar in teste
//  private Y y;
  private final Y y; // #2 constructor injection (recomandat + Lombok❤️)
//  public X(Y y) {
//    this.y = y;
//  }
  public int logic() {
    log.info("Ceva");
    return 1 + y.logic();
  }
}
