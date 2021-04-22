package victor.training.spring.web.controller.dto;

import victor.training.spring.web.domain.Teacher;


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
