package victor.training.spring.web.service;

import org.junit.jupiter.api.Test;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.entity.ProgrammingLanguage;
import victor.training.spring.web.entity.Teacher;
import victor.training.spring.web.entity.Training;
import victor.training.spring.web.repo.TeacherRepo;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.repo.TrainingSearchRepo;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TrainingServiceTest {
  private final TrainingRepo trainingRepo = mock(TrainingRepo.class);
  private final TrainingService trainingService = new TrainingService(
          trainingRepo,
          mock(TrainingSearchRepo.class),
          mock(TeacherRepo.class),
          mock(EmailSender.class),
          mock(TeacherBioClient.class));

  @Test
  void returnsTrainingsWithTheirDetailsInRepositoryOrder() {
    Training javaTraining = training(1L, "Java", "JPA basics", LocalDate.of(2026, 11, 1), 11L, "Alice", ProgrammingLanguage.JAVA);
    Training kotlinTraining = training(2L, "Kotlin", "Coroutines", LocalDate.of(2026, 12, 1), 12L, "Bob", ProgrammingLanguage.KOTLIN);
    when(trainingRepo.findAll()).thenReturn(List.of(javaTraining, kotlinTraining));

    List<TrainingDto> trainings = trainingService.getAllTrainings();

    assertThat(trainings).hasSize(2);
    assertThat(trainings.get(0).id).isEqualTo(1L);
    assertThat(trainings.get(0).name).isEqualTo("Java");
    assertThat(trainings.get(0).description).isEqualTo("JPA basics");
    assertThat(trainings.get(0).startDate).isEqualTo(LocalDate.of(2026, 11, 1));
    assertThat(trainings.get(0).teacherId).isEqualTo(11L);
    assertThat(trainings.get(0).teacherName).isEqualTo("Alice");
    assertThat(trainings.get(0).language).isEqualTo(ProgrammingLanguage.JAVA);
    assertThat(trainings.get(1).id).isEqualTo(2L);
    assertThat(trainings.get(1).name).isEqualTo("Kotlin");
    assertThat(trainings.get(1).teacherName).isEqualTo("Bob");
    assertThat(trainings.get(1).language).isEqualTo(ProgrammingLanguage.KOTLIN);
  }

  @Test
  void returnsAnEmptyListWhenThereAreNoTrainings() {
    when(trainingRepo.findAll()).thenReturn(List.of());

    assertThat(trainingService.getAllTrainings()).isEmpty();
  }

  private Training training(Long id, String name, String description, LocalDate startDate,
                            Long teacherId, String teacherName, ProgrammingLanguage language) {
    Teacher teacher = new Teacher(teacherName);
    teacher.setId(teacherId);

    Training training = new Training();
    training.setId(id);
    training.setName(name);
    training.setDescription(description);
    training.setStartDate(startDate);
    training.setTeacher(teacher);
    training.setProgrammingLanguage(language);
    return training;
  }
}

