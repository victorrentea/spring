package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Experiment {

  //evita default values pentru @Value
  // in schimb, aduna tot configu default in application.properties/.yaml
  // ca sa VEZI ce poti configura, sa nu trebuiasca sa alregi prin cod
//  @Value("${db.pasword:defaultValue}")
  @Value("${db.pasword}") // crapa daca nu gaseste
  private String password;

  @PostConstruct
  public void print() {
    System.out.println("prop = "+ password);
  }
}
