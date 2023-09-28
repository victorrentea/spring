package victor.training.spring.transaction.playground;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
// unique constraint on the message column
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "message"))
public class Message {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull // javax.validation
    @Column
    private String message;

    // la primul acces al colectiilor dintr-o entitate, by default JPA incearca sa le faca lazy-load
    // SELECT 'la botu calului' (*cand accesezi)
    // Conditia ca acest lazy-loading sa mearga este ca Tranzactia sa fie inca deschisa
    // LazyInitializationException daca tranzactia s-a inchis intre timp
//    @ElementCollection
//    private List<String> elemente;
//    @OneToMany
//    @BatchSize(20)
//    private List<AltaEntitate> copii;

    private Message() { // for hibernate only
    }

    public Long getId() {
        return id;
    }

    public Message(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
