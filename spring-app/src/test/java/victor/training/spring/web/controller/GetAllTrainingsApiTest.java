package victor.training.spring.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.entity.ProgrammingLanguage;
import victor.training.spring.web.entity.Teacher;
import victor.training.spring.web.entity.Training;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static victor.training.spring.web.TestDataUtil.javaTraining;

// GET /api/trainings
class GetAllTrainingsApiTest extends ApiTestBase {
  @Test
  void returnsTrainingsWithTheirDetailsInInsertionOrder() {
    trainingRepo.deleteAll(); // the ones InitialData inserted at startup
    Teacher alice = teacherRepo.save(new Teacher("Alice"));
    Teacher bob = teacherRepo.save(new Teacher("Bob"));
    trainingRepo.save(javaTraining().teacher(alice));
    trainingRepo.save(new Training()
            .name("Kotlin")
            .startDate(LocalDate.of(2026, 12, 1))
            .description("Coroutines")
            .programmingLanguage(ProgrammingLanguage.KOTLIN)
            .teacher(bob));

    ResponseEntity<TrainingDto[]> response = rest.getForEntity("/api/trainings", TrainingDto[].class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    TrainingDto[] trainings = response.getBody();
    assertThat(trainings).hasSize(2);

    TrainingDto java = trainings[0];
    assertThat(java.name).isEqualTo("Java");
    assertThat(java.description).isEqualTo("Java description");
    assertThat(java.startDate).isEqualTo(LocalDate.of(2026, 11, 1));
    assertThat(java.teacherId).isEqualTo(alice.id());
    assertThat(java.teacherName).isEqualTo("Alice");
    assertThat(java.language).isEqualTo(ProgrammingLanguage.JAVA);

    TrainingDto kotlin = trainings[1];
    assertThat(kotlin.name).isEqualTo("Kotlin");
    assertThat(kotlin.description).isEqualTo("Coroutines");
    assertThat(kotlin.startDate).isEqualTo(LocalDate.of(2026, 12, 1));
    assertThat(kotlin.teacherId).isEqualTo(bob.id());
    assertThat(kotlin.teacherName).isEqualTo("Bob");
    assertThat(kotlin.language).isEqualTo(ProgrammingLanguage.KOTLIN);
  }

  @Test
  void returnsAnEmptyListWhenThereAreNoTrainings() {
    trainingRepo.deleteAll();

    TrainingDto[] trainings = rest.getForObject("/api/trainings", TrainingDto[].class);

    assertThat(trainings).isEmpty();
  }
}
