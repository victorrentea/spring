package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service // TODO what other annotation register this class as a bean
public class X {
  @Autowired
  private Y y; // #2 field injection

  @Autowired
  private Pojo pojo ;

  // #3 method injection (rarely used)
  // @Autowired public void init(Y y) {this.y2 = y;}
  @PostConstruct
  public void method() {
    System.out.println("POJO:" + pojo);
  }

  public int logic() {
    return 1 + 2;
  }
}
