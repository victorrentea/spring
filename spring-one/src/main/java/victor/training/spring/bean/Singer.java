package victor.training.spring.bean;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Singer {
   @PostConstruct
   public void init() {
      System.out.println("RUN AT INIT singer");
   }
}
