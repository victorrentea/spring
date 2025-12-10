package victor.training.spring.transaction.playground;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter

@Setter
@Table
//@Cacheable //2nd level cache cu ehcache provider ca EhCache (disabled by default in Spring Boot), ne recomandat
public class MyEntity {
  @Id
  @GeneratedValue // din seq
  private Long id;
//  @Id
//  @CustomGenerator... randomUUID
//  private UUID id;
  @NotNull
  @Size(min=3) // java-only
  @Column(unique = true)
  private String name;
  @ElementCollection
//          (fetch = FetchType.EAGER)// ❌❌⚠️⚠️⚠️ NICIODATA CA TE VEDE VLAD MIHALCEA
  private List<String> tags = new ArrayList<>();

  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Child> children = new ArrayList<>();

  protected MyEntity() { // for hibernate only
  }


  public MyEntity(String name) {
    this.name = name;
  }

  public MyEntity addTag(String tag) {
    tags.add(tag);
    return this;
  }
  public MyEntity addChild(Child child) {
    children.add(child);
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
