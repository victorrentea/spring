package victor.training.spring.web.controller.dto;

import victor.training.spring.web.domain.Teacher;

import javax.validation.constraints.NotNull;

public class TeacherDto {
	public Long id;
	@NotNull
	public String name;
	
	public TeacherDto() {
	}

	public TeacherDto(Teacher teacher) {
		this.id = teacher.getId();
		this.name = teacher.getName();
	}
	
	
}
