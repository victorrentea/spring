package victor.training.spring.first.subp;

import org.springframework.stereotype.Component;

@Component // cu de toate, eg un mapper
//@Service // business logic: treburi de prin requirementuri
//@Repository // Manual DB access, nu e necesar daca extinzi o interfata din Spring Data
public class Alta {
  public void f() {
    System.out.println("Sa mearga");
  }
}
