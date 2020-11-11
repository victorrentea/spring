package victor.training.spring.web.controller.dto;

import javax.validation.constraints.NotNull;

public class TrainingDto {
	public Long id;
	public String name;
	public Long teacherId;
	public String teacherName;
	public String startDate;
	public String description;
}
