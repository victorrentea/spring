package victor.training.spring.transactions;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data // nu face asta in viata vietii tale in prod
@Entity
public class Feedback {
   @Id
   @GeneratedValue
   private Long id;
   private String message;
   private Integer score;
}
