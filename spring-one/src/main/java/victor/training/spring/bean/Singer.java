package victor.training.spring.bean;

import lombok.Data;

@Data
public class Singer {
   private final String name;
}

@Data
class Song {
   private final String name;
   private final Singer singer;
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