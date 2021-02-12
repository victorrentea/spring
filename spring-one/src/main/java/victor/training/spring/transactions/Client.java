package victor.training.spring.transactions;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Client {
   @Id
   @GeneratedValue
   private long id;

}
