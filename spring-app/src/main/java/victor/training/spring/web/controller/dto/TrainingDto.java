package victor.training.spring.web.controller.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import victor.training.spring.web.entity.ContractType;
import victor.training.spring.web.entity.ProgrammingLanguage;
import victor.training.spring.web.entity.Training;

import java.time.LocalDate;

public class TrainingDto {
  public Long id;
  @Size(min = 3, max = 50, message = "{customer.name.length}")
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
  public Boolean canDelete; // computed for the current user; ignored on create/update

  public TrainingDto() {
  }

  public TrainingDto(Training training) {
    id = training.id();
    name = training.name();
    description = training.description();
    startDate = training.startDate();
    teacherId = training.teacher().id();
    language = training.programmingLanguage();
    teacherName = training.teacher().name();
    version = training.version();
  }
}
