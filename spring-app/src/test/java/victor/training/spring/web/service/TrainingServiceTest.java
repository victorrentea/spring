package victor.training.spring.web.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import victor.training.spring.web.MyException;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.entity.ProgrammingLanguage;
import victor.training.spring.web.entity.Teacher;
import victor.training.spring.web.entity.Training;
import victor.training.spring.web.repo.TeacherRepo;
import victor.training.spring.web.repo.TrainingRepo;
import victor.training.spring.web.repo.TrainingSearchRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingServiceTest {
  @Mock
  private TrainingRepo trainingRepo;
  @Mock
  private TrainingSearchRepo trainingSearchRepo;
  @Mock
  private TeacherRepo teacherRepo;
  @Mock
  private EmailSender emailSender;
  @Mock
  private TeacherBioClient teacherBioClient;
  @InjectMocks
  private TrainingService service;

  @AfterEach
  void clearSecurityContext() {
    SecurityContextHolder.clearContext();
  }

  private static Teacher teacher(long id, String name) {
    Teacher teacher = new Teacher(id);
    teacher.setName(name);
    return teacher;
  }

  private static Training training(long id, String name) {
    Training training = new Training();
    training.setId(id);
    training.setName(name);
    training.setDescription("desc");
    training.setStartDate(LocalDate.of(2025, 1, 1));
    training.setProgrammingLanguage(ProgrammingLanguage.JAVA);
    training.setVersion(1L);
    training.setTeacher(teacher(7L, "John Doe"));
    return training;
  }

  private static void login(String username) {
    SecurityContextHolder.getContext().setAuthentication(
            new TestingAuthenticationToken(username, "pass"));
  }

  @Test
  void getAllTrainings() {
    when(trainingRepo.findAll()).thenReturn(List.of(training(1L, "Spring")));

    List<TrainingDto> result = service.getAllTrainings();

    assertThat(result).singleElement()
            .satisfies(dto -> {
              assertThat(dto.id).isEqualTo(1L);
              assertThat(dto.name).isEqualTo("Spring");
              assertThat(dto.teacherId).isEqualTo(7L);
            });
  }

  @Test
  void getTrainingById_returnsBio() {
    login("alice");
    when(trainingRepo.findById(1L)).thenReturn(Optional.of(training(1L, "Spring")));
    when(teacherBioClient.retrieveBiographyForTeacher(7L)).thenReturn("Great teacher");

    TrainingDto dto = service.getTrainingById(1L);

    assertThat(dto.name).isEqualTo("Spring");
    assertThat(dto.teacherBio).isEqualTo("Great teacher");
  }

  @Test
  void getTrainingById_bioRetrievalFails() {
    login("alice");
    when(trainingRepo.findById(1L)).thenReturn(Optional.of(training(1L, "Spring")));
    when(teacherBioClient.retrieveBiographyForTeacher(7L))
            .thenThrow(new RuntimeException("boom"));

    TrainingDto dto = service.getTrainingById(1L);

    assertThat(dto.teacherBio).startsWith("<ERROR RETRIEVING TEACHER BIO:");
  }

  @Test
  void getTrainingById_notFound() {
    when(trainingRepo.findById(99L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.getTrainingById(99L))
            .isInstanceOf(NoSuchElementException.class);
  }

  @Test
  void createTraining_saves() {
    Teacher teacher = teacher(7L, "John Doe");
    TrainingDto dto = new TrainingDto();
    dto.name = "Spring";
    dto.description = "desc";
    dto.language = ProgrammingLanguage.JAVA;
    dto.startDate = LocalDate.of(2025, 1, 1);
    dto.teacherId = 7L;
    when(trainingRepo.getByName("Spring")).thenReturn(null);
    when(teacherRepo.getReferenceById(7L)).thenReturn(teacher);

    service.createTraining(dto);

    ArgumentCaptor<Training> captor = ArgumentCaptor.forClass(Training.class);
    verify(trainingRepo).save(captor.capture());
    Training saved = captor.getValue();
    assertThat(saved.getName()).isEqualTo("Spring");
    assertThat(saved.getDescription()).isEqualTo("desc");
    assertThat(saved.getProgrammingLanguage()).isEqualTo(ProgrammingLanguage.JAVA);
    assertThat(saved.getStartDate()).isEqualTo(LocalDate.of(2025, 1, 1));
    assertThat(saved.getTeacher()).isSameAs(teacher);
  }

  @Test
  void createTraining_duplicateNameRejected() {
    TrainingDto dto = new TrainingDto();
    dto.name = "Spring";
    when(trainingRepo.getByName("Spring")).thenReturn(training(1L, "Spring"));

    assertThatThrownBy(() -> service.createTraining(dto))
            .isInstanceOf(MyException.class)
            .extracting(e -> ((MyException) e).getCode())
            .isEqualTo(MyException.ErrorCode.DUPLICATE_TRAINING_NAME);

    verify(trainingRepo, never()).save(ArgumentMatchers.any());
  }

  @Test
  void updateTraining_duplicateNameRejected() {
    TrainingDto dto = new TrainingDto();
    dto.id = 1L;
    dto.name = "Spring";
    when(trainingRepo.countByNameAndIdNot("Spring", 1L)).thenReturn(1);

    assertThatThrownBy(() -> service.updateTraining(dto))
            .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void updateTraining_startDateChanged_sendsEmail() {
    Training existing = training(1L, "Spring");
    existing.setStartDate(LocalDate.of(2025, 1, 1));
    existing.setVersion(1L);
    when(trainingRepo.countByNameAndIdNot("Spring", 1L)).thenReturn(0);
    when(trainingRepo.findById(1L)).thenReturn(Optional.of(existing));

    TrainingDto dto = new TrainingDto();
    dto.id = 1L;
    dto.name = "Spring";
    dto.description = "new desc";
    dto.language = ProgrammingLanguage.KOTLIN;
    dto.teacherId = 7L;
    dto.startDate = LocalDate.of(2025, 6, 1);
    dto.version = 1L;

    service.updateTraining(dto);

    verify(emailSender).sendScheduleChangedEmail(
            existing.getTeacher(), "Spring", LocalDate.of(2025, 6, 1));
    assertThat(existing.getStartDate()).isEqualTo(LocalDate.of(2025, 6, 1));
    assertThat(existing.getName()).isEqualTo("Spring");
    assertThat(existing.getDescription()).isEqualTo("new desc");
    assertThat(existing.getProgrammingLanguage()).isEqualTo(ProgrammingLanguage.KOTLIN);
  }

  @Test
  void updateTraining_sameDate_versionMismatch_skipsEmail() {
    Training existing = training(1L, "Spring");
    existing.setStartDate(LocalDate.of(2025, 1, 1));
    existing.setVersion(5L);
    when(trainingRepo.countByNameAndIdNot("Spring", 1L)).thenReturn(0);
    when(trainingRepo.findById(1L)).thenReturn(Optional.of(existing));

    TrainingDto dto = new TrainingDto();
    dto.id = 1L;
    dto.name = "Spring";
    dto.description = "desc";
    dto.language = ProgrammingLanguage.JAVA;
    dto.teacherId = 7L;
    dto.startDate = LocalDate.of(2025, 1, 1);
    dto.version = 1L;

    service.updateTraining(dto);

    verify(emailSender, never()).sendScheduleChangedEmail(
            ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any());
    assertThat(existing.getStartDate()).isEqualTo(LocalDate.of(2025, 1, 1));
  }

  @Test
  void deleteById() {
    service.deleteById(3L);

    verify(trainingRepo).deleteById(3L);
  }

  @Test
  void search() {
    TrainingSearchCriteria criteria = new TrainingSearchCriteria();
    criteria.name = "Spring";
    when(trainingSearchRepo.search(criteria))
            .thenReturn(List.of(training(1L, "Spring")));

    List<TrainingDto> result = service.search(criteria);

    assertThat(result).singleElement()
            .satisfies(dto -> assertThat(dto.name).isEqualTo("Spring"));
  }

  @Test
  void altaMetoda_returnsCurrentUser() {
    login("bob");

    assertThat(service.altaMetoda()).isEqualTo("bob");
  }
}
