package victor.training.spring.bean;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component// ("conversation") by default the bean name of a @Component&co is the class name with a small first letter
@Data
@RequiredArgsConstructor
public class Conversation {
  private final Person john;
  private final Person jane;

  // @Qualifier at injection point
//  public Conversation(@Qualifier("john") Person one,
//                      @Qualifier("jane") Person two) {

  // or since 5y ago:
//  public Conversation(Person john, Person jane) {
//    // match the param name; Lombok-friendly. Strage to match param name to bean name far away
//    this.one = john;
//    this.two = jane;
//  }

  public void start() {
    System.out.println("This ?");
    System.out.println(john.sayHello());
    System.out.println(jane.sayHello());
  }
}
