package victor.training.spring.web.controller.dto;

import jakarta.validation.constraints.NotNull;
import victor.training.spring.web.entity.Teacher;

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
