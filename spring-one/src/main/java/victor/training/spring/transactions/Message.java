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
//    @Basic(fetch = FetchType.LAZY)
//    @Lob
    private String message;
    @ElementCollection//(fetch = FetchType.EAGER)
//    @Exclue
    private List<Tag> tags = new ArrayList<>();


    public Message setTags(List<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public List<Tag> getTags() {
        return tags;
    }

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
