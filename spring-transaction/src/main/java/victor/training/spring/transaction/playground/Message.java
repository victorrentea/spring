package victor.training.spring.transaction.playground;

import jdk.jfr.DataAmount;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity // MUST HAVE
@Data
@Table(uniqueConstraints = @UniqueConstraint(name = "UQ_MESSAGE", columnNames = "MESSAGE"))
public class Message {
    @Id // MUST HAVE
    @GeneratedValue
    private Long id;
    @NotNull
    private String message;
    @ElementCollection // @OneToMany intr-o tabela din DB cu FK la MESSAGE.ID
    private List<String> tags;

    protected Message() { // for hibernate eyes only
    }

    public Message(String message) {
        this.message = message;
    }
}
