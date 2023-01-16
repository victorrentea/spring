package victor.training.spring.transaction.playground;

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


