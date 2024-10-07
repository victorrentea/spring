package victor.training.spring.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

public class X {
//  programmatic singleton = garbage;
//  private static X INSTANCE;
//  public static X getInstance() {
//    if (INSTANCE == null) {
//      INSTANCE = new X();
//    }
//    return INSTANCE;
//  }
//  private X() {
//  }
  @Autowired // tells spring to inject a Y instance here
  private Y y;

  public int logic() {
    return 1 + y.logic();
  }
}

