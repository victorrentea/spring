package victor.training.spring.web.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Data
@Schema(example = """
        {
         "name": "J",
         "teacherId": 1
         }
    """)
public class TrainingSearchCriteria {
   public String name;
   public Long teacherId;
}
