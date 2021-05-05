package victor.training.spring.aspects;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data // because it's a demo
public class User {
   @Id
   @GeneratedValue
   private Long id;
   private String name;
}
