package victor.training.spring.transaction.playground;

import jdk.jfr.DataAmount;
import lombok.Data;
import org.springframework.lang.Nullable;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity // you are using jooq: less magic, supports reactive
@Data
@Table(uniqueConstraints = @UniqueConstraint(name = "UQ_MESSAGE", columnNames = "MESSAGE"))
public class Message {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String message;

    protected Message() { // for hibernate eyes only
    }

    public Message(String message) {
        this.message = message;
    }
}
