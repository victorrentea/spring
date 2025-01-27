package victor.training.spring.web.controller.dto;

import victor.training.spring.web.entity.Teacher;

import jakarta.validation.constraints.NotNull;

public class TeacherDto {
	public Long id;
	@NotNull(message = "{teacher.name.mandatory}")
	public String name;
	
	public TeacherDto() {
	}

	public TeacherDto(Teacher teacher) {
		this.id = teacher.getId();
		this.name = teacher.getName();
	}
	
	
}
