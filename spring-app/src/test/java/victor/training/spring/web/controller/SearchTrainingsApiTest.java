package victor.training.spring.web.controller;

import org.junit.jupiter.api.Test;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.entity.Teacher;
import victor.training.spring.web.entity.Training;

import static org.assertj.core.api.Assertions.assertThat;
import static victor.training.spring.web.TestDataUtil.javaTraining;

// GET /api/trainings/search2
class SearchTrainingsApiTest extends ApiTestBase {
  @Test
  void searchesByNameGivenAsQueryParam() {
    trainingRepo.deleteAll();
    Teacher alice = teacherRepo.save(new Teacher("Alice"));
    trainingRepo.save(javaTraining().teacher(alice));
    trainingRepo.save(new Training().name("Kotlin").teacher(alice));

    TrainingDto[] trainings = rest.getForObject("/api/trainings/search2?name=Java", TrainingDto[].class);

    assertThat(trainings).extracting(t -> t.name).containsExactly("Java");
  }
}
