package victor.training.spring.web.controller.dto;


import lombok.Data;

// creat automat de Jackson din JSON
public class TrainingSearchCriteria {
   public String namePart;
   public String descriptionPart;
   public Long teacherId;
}
