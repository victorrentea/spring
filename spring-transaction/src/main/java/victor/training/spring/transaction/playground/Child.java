package victor.training.spring.transaction.playground;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Child {
  @Id
  @GeneratedValue
  private Long id;

  private String name;
  private String a;
  private String b;

  @OneToMany(cascade = CascadeType.ALL)
  private List<Pisica> pisici = new ArrayList<>();

  @ManyToOne
  private MyEntity parent;

  Child addPisica(Pisica pisica) {
    pisici.add(pisica);
    return this;
  }

}
interface ChildRepo extends JpaRepository<Child, Long> {
  List<Child> findByParentIdIn(List<Long> parentIds);
}
