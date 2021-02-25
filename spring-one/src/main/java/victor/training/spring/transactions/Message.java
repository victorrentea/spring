package victor.training.spring.transactions;

import javax.persistence.*;

@Entity
//@NamedQueries({
//    @NamedQuery(name = "a", query = "SELECT m FROM Message m WHERE m.message = ?1")
//})
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

    public Message setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "Message{" +
               "id=" + id +
               ", message='" + message + '\'' +
               '}';
    }
}
