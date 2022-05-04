package victor.training.spring.web.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class ProgrammingLanguage {
   @Id
   @GeneratedValue
   private Long id;
   private String name;

   protected ProgrammingLanguage() {}
   public ProgrammingLanguage(String name) {
      this.name = name;
   }

   public String getName() {
      return name;
   }
}
