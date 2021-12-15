package victor.training.spring.transactions;

import javax.persistence.*;

@Entity
//@NamedQueries(@NamedQuery(name = "ciocan.rosu", query = "SELCT m FROM Message"))
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
