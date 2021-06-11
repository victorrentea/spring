package victor.training.spring.web.controller.dto;

import javax.validation.constraints.Size;

public class TrainingDto {
	public String id;
	@Size(min = 2)
	public String name;
	public Long teacherId;
	public String teacherName;
	public String startDate;
	public String description;
}
