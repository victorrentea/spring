package victor.training.spring.transaction.playground;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull // JPA NU VA PUNE NICIODATA NULL IN DB pe aceasta coloana
    private String message/* = "n/a"*/;

//    private boolean flag;
//    private LocalDateTime lastChanged;

    private Message() { // for hibernate only
    }

    public Message(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
