package victor.training.spring.async;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class EmailDeTrimis {
  @Id
  @GeneratedValue
  private Long id;
  private String email;
}
