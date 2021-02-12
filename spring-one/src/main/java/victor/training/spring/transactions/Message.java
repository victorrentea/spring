package victor.training.spring.transactions;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Message {
   @Id
   @GeneratedValue
   private Long id;
   private String message;
   private LocalDateTime createTime;
   @ManyToOne
   private Client client;

   protected Message() {
   }

   @PostLoad
   public void afterSELECT() {}

   @PrePersist
   public void beforeInsert(){

   }

   public Message(String message) {
      this.message = message;
   }

}
