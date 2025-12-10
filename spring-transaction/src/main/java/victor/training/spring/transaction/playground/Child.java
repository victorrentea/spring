package victor.training.spring.transaction.playground;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

@Entity
@Data
public class Child {
  @Id
  @GeneratedValue
  private Long id;

  private String name;

  @ManyToOne
  private MyEntity parent;

}
interface ChildRepo extends JpaRepository<Child, Long> {
  List<Child> findByParentIdIn(List<Long> parentIds);
}
