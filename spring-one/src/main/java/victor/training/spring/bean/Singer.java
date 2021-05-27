package victor.training.spring.bean;

import javax.annotation.PostConstruct;

public class Singer {
   private final String name;

   public Singer(String name) {
      System.out.println("Se naste " + name);
      this.name = name;
   }

   @PostConstruct
   public void antreneaza() {
      System.out.println("Cant in baie");
   }
}
