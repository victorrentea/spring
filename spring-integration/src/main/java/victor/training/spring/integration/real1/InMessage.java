package victor.training.spring.integration.real1;

import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class InMessage {
  @Id
  @GeneratedValue
  private Long id;
  private String name;
  private LocalDateTime createdAt = LocalDateTime.now();
  @ElementCollection(fetch = FetchType.EAGER)
  private List<String> transfers = new ArrayList<>();
}
