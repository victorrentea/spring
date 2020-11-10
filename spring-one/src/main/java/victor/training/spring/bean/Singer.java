package victor.training.spring.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Singer {
   @Autowired
   private Alta alta;

   public Alta getAlta() {
      return alta;
   }

   @PostConstruct
   public void init() {
      System.out.println("RUN AT INIT singer");
   }
}

@Component
class Alta {

}



@Component
class Ying {
   @Autowired
   Yang yang;
   @PostConstruct
   public void init() {
      System.out.println("RUN AT yang: " + yang.ying);
   }
}
@Component
class Yang {
   @Autowired
   Ying ying;
   @PostConstruct
   public void init() {
      System.out.println("RUN AT ying: "+ ying.yang);
   }
}