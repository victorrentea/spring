package victor.training.spring.transaction.playground;

import jdk.jfr.DataAmount;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(uniqueConstraints =
@UniqueConstraint(columnNames = "MESSAGE", name = "UQ_MESSAGE"))
public class Message {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String message;
    @ElementCollection
    private List<String> tags = new ArrayList<>();

    protected Message() { // for hibernate only
    }

    public Message(String message) {
        this.message = message;
    }
}
