package victor.training.spring.web.controller.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TrainingDto {
	public Long id;
	@Size(min = 3, max = 50, message = "{training.name.length}")
	@NotNull
//	@ValidTrainingName
	public String name;
	public Long teacherId;
	public String teacherName;
	public String externalSYstemUUID;
	@NotNull
	public String startDate;
	public String description;
}
