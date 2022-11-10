package victor.training.spring.web.controller.dto;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import victor.training.spring.web.entity.Teacher;

import javax.validation.constraints.NotNull;

public class TeacherDto {
	public Long id;
	@NotNull
	@Schema(description = "Numele teacherului")
	public String name;
	
	public TeacherDto() {
	}

	public TeacherDto(Teacher teacher) {
		this.id = teacher.getId();
		this.name = teacher.getName();
	}
	
	
}
