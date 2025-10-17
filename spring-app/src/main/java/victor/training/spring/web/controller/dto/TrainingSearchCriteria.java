package victor.training.spring.web.controller.dto;

import lombok.Data;
import lombok.ToString;

@Data
public class TrainingSearchCriteria {
   public Long id; // the WWW users should not probably see the numeric PK, but UUIDs (external key)(!
   public String name;
   public Long teacherId;
}
