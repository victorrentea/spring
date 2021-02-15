package victor.training.spring.transactions;

import javax.persistence.*;

//@NamedQueries({
//    @NamedQuery(name = "q1", query = "SELECT m FROM Mesage")
//})
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

}
