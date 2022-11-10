package victor.training.spring.transaction.playground;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "MESSAGE"))
public class Message {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
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



