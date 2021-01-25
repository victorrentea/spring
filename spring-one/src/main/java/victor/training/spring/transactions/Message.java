package victor.training.spring.transactions;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Message {
    @Id
    @GeneratedValue
    private Long id;
    private String message;
    private String bigText; //  10 MB  CHARS- - > CLOB

    protected Message() {
    }

    public Message(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    public Message(String message) {
        this.message = message;
    }

}
