package victor.training.spring.transaction.playground;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

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

    protected Message() { // for hibernate only
    }

    public Message(String message) {
        this.message = message;
    }

  public Message addTag(String tag) {
    tags.add(tag); // => +1 INSERT
    return this;
  }
}
