package victor.training.performance.batch.core.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@SequenceGenerator(name = "myseq", allocationSize = 100)
public class Person {
    @Id
    @GeneratedValue(generator = "myseq")
    private Long id; // ELEGANT: UUID
    private String name;
    @ManyToOne
    private City city;
    public Person(String name) {
        this.name = name;
    }
    public Person() {}
}
