package victor.training.spring.transactions;

import javax.persistence.*;

@Entity
public class Message {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
//    @Basic(fetch = FetchType.LAZY)
//    @Lob
    private String message;
//    @OneTo

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
