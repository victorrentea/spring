package victor.training.spring.web;

import victor.training.spring.web.entity.ProgrammingLanguage;
import victor.training.spring.web.entity.Teacher;
import victor.training.spring.web.entity.Training;

import java.time.LocalDate;

// Object Mother: the well-known test data, reused by all tests
public class TestDataUtil {
  public static Training javaTraining(Teacher teacher) {
    return new Training("Java", LocalDate.of(2026, 11, 1))
            .setDescription("JPA basics")
            .setProgrammingLanguage(ProgrammingLanguage.JAVA)
            .setTeacher(teacher);
  }
}
