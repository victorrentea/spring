package victor.training.spring.web;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Message2 {
   @Id
   @GeneratedValue
   private Long id;
   private String message;

   public Message2(String message) {
      this.message = message;
   }
}
