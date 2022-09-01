package victor.training.spring.web.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
@Data
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
