package victor.training.spring.transaction.playground;

import jdk.jfr.DataAmount;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.lang.Nullable;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(uniqueConstraints =
@UniqueConstraint(
    columnNames = "MESSAGE",
    name = "UQ_MESSAGE"))
@DynamicUpdate
// 1 viteza(update mai mic)
// 2 mai mica sansa de race:
// - user1 modifica doar camp1
// - user2 modifica doar camp2 => nu se suprascriu cu@DynamicUpdate
public class Message {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String message;
    private String altCamp = "init";
    @ElementCollection
    private List<String> tags = new ArrayList<>();

    protected Message() { // for hibernate only
    }

    public Message(String message) {
        this.message = message;
    }
}
