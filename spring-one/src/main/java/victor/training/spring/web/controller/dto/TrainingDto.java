package victor.training.spring.web.controller.dto;


import victor.training.spring.web.validation.ValidTrainingName;

public class TrainingDto {
	public Long id;
	@ValidTrainingName
//	@Size(min = 3, max = 50)
	public String name;
	public Long teacherId;
	public String teacherName;
	public String startDate;
	public String description;
}
