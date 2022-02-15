package victor.training.spring.transactions;

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

    public List<String> getTags() {
        return tags;
    }

    public Message addTag(String tag) {
        tags.add(tag);
        return this;
    }

    private Message() { // for hibernate only
    }

    public Message(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
