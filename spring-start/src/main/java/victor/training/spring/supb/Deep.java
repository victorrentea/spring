package victor.training.spring.supb;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Deep {
  @Value("${minOrderAmountForDiscount}") // fails if prop not define
//  @Value("${minOrderAmountForDiscount:0}") // provide a default < avoid this
  // you have to scan the code to find the available props to set.
  private int minOrderAmountForDiscount;

  @PostConstruct
  public void logic() {
    System.out.println("value: "+ minOrderAmountForDiscount);
  }
}
