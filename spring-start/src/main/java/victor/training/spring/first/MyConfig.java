package victor.training.spring.first;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

//@Configuration //no application logic, only Spring glue, beans, annotation ,.. etc
@Import({
  YConfig.class
})
public class MyConfig {// implements somth from Spring
  private final Y y;

  public MyConfig(Y y) {
    this.y = y;
  }

  @Bean
  public X x() {
    return new X(y);
  }
}
class YConfig {
  @Bean
  public Y y() {
    return new Y();
  }
}