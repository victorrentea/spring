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

    // not pre-loaded upfront, LAZY LOADED
    @ElementCollection
    private List<String> phones = new ArrayList<>();

    private Message() { // for hibernate only
    }

    public List<String> getPhones() {
        return phones;
    }

    public Message(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
