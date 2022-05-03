package victor.training.spring.transaction.exercises.exceptions;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class ExceptionalEntity {
   @Id
   @GeneratedValue
   private Long id;
   private String name;

   protected ExceptionalEntity() {} // for Hibernate
   public ExceptionalEntity(String name) {
      this.name = name;
   }

}
