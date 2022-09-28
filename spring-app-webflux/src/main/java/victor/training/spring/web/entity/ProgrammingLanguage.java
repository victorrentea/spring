package victor.training.spring.web.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;


@Data
public class ProgrammingLanguage {
   @Id
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
