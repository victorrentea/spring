package victor.training.spring.transaction.playground;

import javax.persistence.*;
import java.sql.Blob;

@Entity
public class Message {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    @Lob
    private String message;

//    @Lob
//    private byte[] pdf;
    private Blob pdf;

    public Long getId() {
        return id;
    }

    private Message() { // for hibernate only
    }

    public Message(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
