package victor.training.spring.transaction.exercises.jpa;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data // avoid @Data + @Entity in production
public class JpaEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    @ElementCollection
    private List<String> tags = new ArrayList<>();

    protected JpaEntity() {
    } // for Hibernate

    public JpaEntity(String name, String... tags) {
        this.name = name;
        this.tags.addAll(List.of(tags));
    }

}

