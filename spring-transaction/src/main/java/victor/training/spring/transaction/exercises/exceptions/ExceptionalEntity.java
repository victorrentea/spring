package victor.training.spring.transaction.exercises.exceptions;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ExceptionalEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    protected ExceptionalEntity() {
    } // for Hibernate

    public ExceptionalEntity(String name) {
        this.name = name;
    }

}
