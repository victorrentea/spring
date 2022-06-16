package victor.training.spring.transaction.playground;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Message {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String message;

    @ElementCollection
    private List<String> tags = new ArrayList<>();

    private Message() { // for hibernate only
    }

    public Long getId() {
        return id;
    }

    public List<String> getTags() {
        return tags;
    }

    public Message(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
