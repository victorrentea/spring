package victor.training.spring.first.subp;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import victor.training.spring.first.PreDI;

@Component // cu de toate, eg un mapper
//@Service // business logic: treburi de prin requirementuri
//@Repository // Manual DB access, nu e necesar daca extinzi o interfata din Spring Data
//@RequiredArgsConstructor
public class Alta {

  @Autowired
//  @Lazy // de evitat
  private PreDI preDI;

  public void f() {
    System.out.println("Sa mearga");
  }
}
