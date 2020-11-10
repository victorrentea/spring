package victor.training.spring.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
public class Singer {
   private final String name;
   @PostConstruct
   public void init() {
      System.out.println("Urlu pe note!! Uaaa!");
   }
}

@Data
class Song {
   private final String name;
   private final Singer singer;
}


//@Component
//class oarecareFiecare {
//
//}

@Component("iarta-ma")
class OarecareFiecare {

}



//@Component
//class Ying {
//   final Yang yang;
//
//   public Ying(Yang yang) {
//      this.yang = yang;
//   }
//
//   @PostConstruct
//   public void init() {
//      System.out.println("RUN AT yang: " + yang.ying);
//   }
//}
//@Component
//class Yang {
//   final Ying ying;
//
//   public Yang(Ying ying) {
//      this.ying = ying;
//   }
//
//   @PostConstruct
//   public void init() {
//      System.out.println("RUN AT ying: "+ ying.yang);
//   }
//}