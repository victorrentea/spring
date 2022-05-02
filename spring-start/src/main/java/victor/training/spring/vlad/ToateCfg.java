package victor.training.spring.vlad;

import lombok.Data;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data // ! f important
@Component
@ConfigurationProperties
public class ToateCfg {
   public enum ModelBMW {
      one,
      two
   }
   private Map<ModelBMW, Cfg> map;

   @EventListener(ApplicationStartedEvent.class)
   public void printMe() {
      System.out.println(this);
      for (ModelBMW value : ModelBMW.values()) {

         if (!map.containsKey(value)) {
            throw new IllegalArgumentException("NU !");
         }
      }

   }
}
