package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

public class Cris {
  private final X x;

  private final String prop;

  public Cris(X x,
            @Value("${mail.sender}") String prop) {
    this.x = x;
    this.prop = prop;
  }

  @PostConstruct
  public void logicMethod() {
    System.out.println("Cris logic: " + prop);
  }
}
