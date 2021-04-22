package victor.training.spring.transactions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Message {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String message;

    protected Message() {
    }

    public Message(String message) {
        this.message = message;
    }

    public Message setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Long getId() {
        return id;
    }
}
