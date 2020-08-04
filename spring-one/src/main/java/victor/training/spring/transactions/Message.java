package victor.training.spring.transactions;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Message {
    @Id
    @GeneratedValue
    @Getter
    private Long id;
    private String message;

    protected Message() {
    }

    public Message(String message) {
        this.message = message;
    }

}
