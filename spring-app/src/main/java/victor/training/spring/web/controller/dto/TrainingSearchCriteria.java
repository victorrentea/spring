package victor.training.spring.web.controller.dto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class TrainingSearchCriteria {
   public String name;
   public Long teacherId;
}
