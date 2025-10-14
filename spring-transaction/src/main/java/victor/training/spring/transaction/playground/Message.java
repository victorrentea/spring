package victor.training.spring.transaction.playground;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "MESSAGE", name = "UQ_MESSAGE"))
public class Message {
  @Id
  @GeneratedValue
  private Long id;
  @NotNull
  private String message;
  @ElementCollection
  private List<String> tags = new ArrayList<>();
  @Version// JPA optimistic locking
  private Long version;

  protected Message() { // for hibernate only
  }

  public Message(String message) {
    this.message = message;
  }

  public Message addTag(String tag) {
    tags.add(tag); // => +1 INSERT
    return this;
  }

  public String toString() {
    return "Message(id=" + this.getId() +
           ", message=" + this.getMessage() +
           //", tags=" + this.getTags() +
           ", version=" + this.getVersion() + ")";
  }
}
