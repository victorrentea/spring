package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// face clasa un Bean de Spring
//@RequiredArgsConstructor
@Service // = e o clasa cu logica
public class X {
//  @Autowired
//  private Y y; // #2 field injection cu reflection chiar daca e privat

  private final Y y;
  public X(Y y) { // #1 constructo injection e recomandat. de ce: mai usor de testat, si mai imutabil❤️
    this.y = y;
  }

//   #3 method injection (rarely used)
//   @Autowired
//   public void init(Y y) {this.y2 = y;}

  public int logic() {
    return 1 + y.logic();
  }
}
