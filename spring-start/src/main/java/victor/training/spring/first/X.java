package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO Define Beans
@Service // ii spune lui spring sa manageuie o instanta din aceasta clasa
@RequiredArgsConstructor // ii spune lui Lombok sa genereze ctor pt toate campurile finale < #2 constructor injection
@Slf4j // + private static final Logger log = LoggerFactory.getLogger(X.class);
public class X { // spring ii face "new"
//  @Autowired // #1 field injection - descurajat astazi
//  private Y y; // nici unui framework in Java nu ii pasa de private: Spring, Hibernate, EJB, CDI, Jackson
  // foloseste Java reflection sa acceseze campuri private
  private final Y y;

  @Autowired //method/setter- injection
  public void metoda(Y y) {
    log.info("Am primit: " + y);
  }

  public int logic() {
    log.info("X.logic");
    return 1 + y.logic();
  }
}
