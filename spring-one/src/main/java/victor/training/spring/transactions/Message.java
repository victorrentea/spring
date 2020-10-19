package victor.training.spring.transactions;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Message {
    @Id
    @GeneratedValue
    private Long id;
    private String message;

    public Long getId() {
        return id;
    }

    protected Message() {
    }

    public Message(String message) {
        this.message = message;
    }

}
