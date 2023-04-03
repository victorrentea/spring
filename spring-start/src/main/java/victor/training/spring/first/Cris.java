package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
public class Cris {
  private final X x;
  private final String prop;

  @PostConstruct
  public void logicMethod() {
    System.out.println("Cris logic: " +prop);
  }
}
