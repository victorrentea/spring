package victor.training.spring.web.controller.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import victor.training.spring.web.entity.ContractType;
import victor.training.spring.web.entity.ProgrammingLanguage;
import victor.training.spring.web.entity.Training;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class TrainingDto {
	public Long id;
	@Size(min = 3, max = 50, message = "{customer.name.length}")
	public String name;
	public ContractType level;
	public Long teacherId;
	public String teacherBio;
	public ProgrammingLanguage language;
	public String teacherName;
	@JsonFormat(pattern = "dd-MM-yyyy")
	public LocalDate startDate;
	public String description;
	public Long version;

	public TrainingDto() {}

	public TrainingDto(Training training) {
		id = training.getId();
		name = training.getName();
		description = training.getDescription();
		startDate = training.getStartDate();
		teacherId = training.getTeacher().getId();
		language = training.getProgrammingLanguage();
		teacherName = training.getTeacher().getName();
		version = training.getVersion();
	}
}
