package victor.training.spring.transaction.exercises.jpa;

import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

   protected JpaEntity() {} // for Hibernate

   public JpaEntity(String name, String... tags) {
      this.name = name;
      this.tags.addAll(List.of(tags));
   }

}

