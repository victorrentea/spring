package victor.training.spring.transaction.playground;

import jdk.jfr.DataAmount;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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
    @Embedded // cel mai 😎 feat din JPA nefolosit.
    private Garantii garantii;

    protected Message() { // for hibernate only
    }

    public Message(String message) {
        this.message = message;
    }
}
@Embeddable // adica unde-l includ in @Entity nu reprezinta
// tabela separata ci doar un set de coloane in tabela ent container
record Garantii(
    String casa,
    String masina) {
}