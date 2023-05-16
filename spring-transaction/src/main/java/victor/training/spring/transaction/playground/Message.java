package victor.training.spring.transaction.playground;

import lombok.Data;

import javax.persistence.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Data // lombook + entity = horror
@Entity
@Table(
    uniqueConstraints = @UniqueConstraint(columnNames = {"MESSAGE"})
)
public class Message {
  @Id
  @GeneratedValue
  private Long id;
  @Column(nullable = false) // NOT NULL in db
  private String message;
  @ElementCollection // imaginativa @OneToMany catre alta @Entity
  private List<String> tags = new ArrayList<>();

  public List<String> getTags() {
    return tags;
  }

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
