package victor.training.spring.web;

import victor.training.spring.web.entity.ProgrammingLanguage;
import victor.training.spring.web.entity.Training;

import java.time.LocalDate;

// Object Mother: the well-known test data, reused by all tests
public class TestDataUtil {
  public static Training javaTraining() {
    return new Training()
            .name("Java")
            .startDate(LocalDate.of(2026, 11, 1))
            .description("Java description")
            .programmingLanguage(ProgrammingLanguage.JAVA);
  }
}
