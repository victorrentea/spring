package victor.training.spring.transaction.playground;

import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@ToString
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "MESSAGE")) // TODO victorrentea 2023-06-15:
public class Message {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false) // NOT NULL pe coloana
    @NotEmpty
    private String message;

    private Message() { // for hibernate only
    }

    public Message(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
