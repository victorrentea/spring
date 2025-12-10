package victor.training.spring.transaction.playground;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Pisica {
  @Id
  @GeneratedValue
  private Long id;

  private String nume;
}
