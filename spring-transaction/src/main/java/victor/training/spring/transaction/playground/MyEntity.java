package victor.training.spring.transaction.playground;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter

@Setter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "NAME", name = "UQ_NAME"))
public class MyEntity {
  @Id
  @GeneratedValue
  private Long id;
  @NotNull
  @Column
  private String name;
  @ElementCollection
  private List<String> tags = new ArrayList<>();

  protected MyEntity() { // for hibernate only
  }

  public MyEntity(String name) {
    this.name = name;
  }

  public MyEntity addTag(String tag) {
    tags.add(tag);
    return this;
  }

  @Override
  public String toString() {
    return "MyEntity{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
  }
}
