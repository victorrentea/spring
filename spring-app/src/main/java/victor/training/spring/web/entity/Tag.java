package victor.training.spring.web.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Tag {
   @Id
   @GeneratedValue
   private Long id;
   private String name;

   protected Tag() {}
   public Tag(String name) {
      this.name = name;
   }

   public String getName() {
      return name;
   }
}
