package victor.training.spring.web.controller.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TrainingDto {
	public Long id;
	@Size(min = 3, max = 50)
//	@ValidTrainingName
	public String name;
	@NotNull
	public Long teacherId;
	public String teacherName;
	public String startDate;
	@NotNull
	public String description;
}
