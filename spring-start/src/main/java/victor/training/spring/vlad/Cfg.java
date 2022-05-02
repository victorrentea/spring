package victor.training.spring.vlad;

import lombok.Data;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Data
public class Cfg {
   private Integer x;
   private Integer y;

@PostConstruct
   public void validate() {
      Objects.requireNonNull(x);
      Objects.requireNonNull(y);
   }
}
