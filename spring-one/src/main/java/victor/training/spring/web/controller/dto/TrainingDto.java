package victor.training.spring.web.controller.dto;

import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ToString
public class TrainingDto {
	public Long id;
	@Size(min = 2)
	@NotNull
	private String name;
	public Long teacherId;
	public String teacherName;
	public String startDate;
	public String description;

	public String getName() {
		return name;
	}

	public TrainingDto setName(String name) {
		this.name = name;
		return this;
	}
}
