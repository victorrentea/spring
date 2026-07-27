package victor.training.spring.web.controller.dto;

import jakarta.validation.constraints.NotNull;
import victor.training.spring.web.entity.Teacher;

public class TeacherDto {
  public static class Groups {
    public interface Draft {}
    public interface Final {}
  }
  public Long id;
  @NotNull(groups = Groups.Final.class)
  public String name;

  public TeacherDto() {
  }

  public TeacherDto(Teacher teacher) {
    this.id = teacher.getId();
    this.name = teacher.getName();
  }


}
