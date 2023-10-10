package victor.training.spring.transaction.playground;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "UQ_MESSAGE", columnNames = "message"))
public class Message {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
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
