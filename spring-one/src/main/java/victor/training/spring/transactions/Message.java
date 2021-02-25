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

    protected Message() {
    }

    public Message(String message) {
        this.message = message;
    }

    public Message(long id, String message) {
        this.id = id;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
               "id=" + id +
               ", message='" + message + '\'' +
               '}';
    }
}
