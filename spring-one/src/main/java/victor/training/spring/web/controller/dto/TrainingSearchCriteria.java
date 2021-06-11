package victor.training.spring.web.controller.dto;

public class TrainingSearchCriteria {
   public String name;
   public Long teacherId;
   public String sortBy;
   public SortColEnum colValue;
}
enum SortColEnum {
   id, last_name, first_name
}
