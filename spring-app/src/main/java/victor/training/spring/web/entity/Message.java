package victor.training.spring.web.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

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
