package victor.training.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import victor.training.spring.first.Y;

@Service // = singleton, 1 instanta / app
public class X {
  @Autowired
  private Y y;

  private String username;

  public int logic(String user) {//poate fi chema din 200+ threadurir in parallel
    username = user;
    f();
    return 1 + y.logic();
  }

  private void f() {
    System.out.println("Userul = " + username); // RACE CONDITION
    // pt ca username e camp de instanta, shared intre threaduri
    //  si fiecare thread suprascrie valoarea celuilalt
  }
}
