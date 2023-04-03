package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
public class Cris {
  private final X x;
  @Value("${mail.sender}")
  private final String prop;

  @PostConstruct
  public void logicMethod() {
    System.out.println("Cris logic: " + prop);
  }
}


@Component
class SomeBeanNoOneLoves {

  @PostConstruct
  public void method() {
    System.out.printf(" I still live !");
  }
}