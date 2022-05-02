package victor.training.spring.vlad;

import lombok.Data;

@Data
public class Cfg {
   private int x;
   private int y;

   public Cfg(int x, int y) {
      this.x = x;
      this.y = y;
   }
}
