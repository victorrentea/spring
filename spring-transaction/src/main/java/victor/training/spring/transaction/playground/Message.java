package victor.training.spring.transaction.playground;

import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(uniqueConstraints =
  @UniqueConstraint(name = "UQ_MESSAGE", columnNames = "MESSAGE"))
public class Message {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String message;

    @ElementCollection
    private List<String> hashtag = new ArrayList<>();

    protected Message() { // for hibernate eyes only
    }

    public Message(String message) {
        this.message = message;
    }
}
