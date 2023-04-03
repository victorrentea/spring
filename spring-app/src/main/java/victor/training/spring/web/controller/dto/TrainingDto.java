package victor.training.spring.web.controller.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import victor.training.spring.web.entity.ContractType;
import victor.training.spring.web.entity.ProgrammingLanguage;
import victor.training.spring.web.entity.Training;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class TrainingDto {
	public Long id;
	@NotNull
	@Schema(description = "name of training, duhh, but I'm paid by the lines of code")
	@Size(min = 3, max = 50, message = "{training.name.length}")
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
