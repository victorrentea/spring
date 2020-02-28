package victor.training.spring.spa.controller.dto;

import victor.training.spring.spa.domain.Teacher;

public class TeacherDto {
	public Long id;
	public String name;
	
	public TeacherDto() {
	}

	public TeacherDto(Teacher teacher) {
		this.id = teacher.getId();
		this.name = teacher.getName();
	}
	
	
}
