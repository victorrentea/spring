package victor.training.spring.web.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
 @Data // typedef struct { } < NON OOP
@NoArgsConstructor
public class Message {
   @Id
   @GeneratedValue
   private Long id;
   private String message;

   public Message(String message) {
      this.message = message;
   }
}
