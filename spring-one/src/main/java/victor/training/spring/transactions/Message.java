package victor.training.spring.transactions;

import lombok.EqualsAndHashCode.Exclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


class MessageData{

}

@Entity
public class Message {
    @Id
    @GeneratedValue
    @Getter

    private Long id;
    @Setter
    @Getter
    private String message;
    @OneToMany(mappedBy = "message")
    private List<ErrorLine> errorLines = new ArrayList<>();

    protected Message() {
    }

    {
        Message message1 = new Message();
        ErrorLine e = new ErrorLine();
        message1.getErrorLines().add(e);
        e.setMessage(message1);
    }

    // 1) inglobezi niste reguli de buz, un mic state machine
//    void activate() {
//        if (status != DRAFT) {
//            throw new IllegalStateException();
//        }
//        status=ACTIVE;
//    }

    // 2) ai grija de invarianti din model: bidirectional ,sa cresti Order.Total cand Order.addLine
    // tODO REMOVE
    // tODO CLEAR
    // tODO ADDALL
    public void addErrorLine(ErrorLine line) {
        errorLines.add(line);
        line.setMessage(this);
    }
    public List<ErrorLine> getErrorLines() {
        return Collections.unmodifiableList(errorLines);
    }

    // 3) HashCode/equals SA NU CARE CUMVA sa il implementati pe entitati.
    /**  @see    lombok.EqualsAndHashCode.Exclude */
    // 4) @ToString pe OneToMany lazily loaded


    public Message setId(Long id) {
        this.id = id;
        return this;
    }
    public Message(String message) {
        this.message = message;
    }
}

@Entity
class ErrorLine {
    @Id
    private Long id;
    private String name;

    @ManyToOne
    Message message;

    void setMessage(Message message) {
        this.message = message;
    }
}
