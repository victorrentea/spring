package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component// ("conversation") by default the bean name of a @Component&co is the class name with a small first letter
@Data
public class Conversation {
  private final Person one;
  private final Person two;

  // @Qualifier at injection point
  public Conversation(@Qualifier("john") Person one,
                      @Qualifier("jane") Person two) {
    this.one = one;
    this.two = two;
  }

  public void start() {
    System.out.println("This ?");
    System.out.println(one.sayHello());
    System.out.println(two.sayHello());
  }
}
