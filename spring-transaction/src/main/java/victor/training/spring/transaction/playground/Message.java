package victor.training.spring.transaction.playground;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
// unique constraint on the message column
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "message"))
public class Message {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull // javax.validation
    @Column
    private String message;

    private Message() { // for hibernate only
    }

    public Long getId() {
        return id;
    }

    public Message(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
