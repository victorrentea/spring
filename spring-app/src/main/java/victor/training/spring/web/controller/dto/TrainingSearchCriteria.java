package victor.training.spring.web.controller.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = false, chain = true) // setName(..) needed by Spring MVC to bind ?name=.. into this object
public class TrainingSearchCriteria {
  public String name;
  public Long teacherId;
}
