package victor.training.spring.ai;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
class Dog {
  @Id
  @GeneratedValue
  private Integer id;
  private String name;
  private String description;
  private String owner;
  private String vectorId = UUID.randomUUID().toString();
  // ~ FK to vector store entry
}
