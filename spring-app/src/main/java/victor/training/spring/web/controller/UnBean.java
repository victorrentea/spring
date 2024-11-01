package victor.training.spring.web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UnBean {
  public boolean daVoie(Long trainingId) {
//    SecurityContextHolder.getContext()...
    System.out.println("Verfici daca userul curent are depreetul pe " + trainingId);
    return true;
  }
}
