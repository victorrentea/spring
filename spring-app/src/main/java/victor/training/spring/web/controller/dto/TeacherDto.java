package victor.training.spring.web.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import victor.training.spring.web.entity.Teacher;

import jakarta.validation.constraints.NotNull;

public class TeacherDto {
	public Long id;
	@NotBlank
	@Pattern(regexp = "[A-Za-z\\-]+")
	@Size(min = 2, max = 100)
	public String name ="XÆA-Xii";
	
	public TeacherDto() {
	}

	public TeacherDto(Teacher teacher) {
		this.id = teacher.getId();
		this.name = teacher.getName();
	}
	
	
}
