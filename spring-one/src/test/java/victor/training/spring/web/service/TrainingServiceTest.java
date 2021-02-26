package victor.training.spring.web.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.domain.Teacher;
import victor.training.spring.web.domain.Training;
import victor.training.spring.web.repo.TeacherRepo;
import victor.training.spring.web.repo.TrainingRepo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;


class TrainingServiceTest extends RepoTestBase {
   @Autowired
   private TrainingService service;
   @Autowired
   private TrainingRepo repo;
   @MockBean
   private EmailSender emailSenderMock;

   @RegisterExtension
  public WithCountryRefData withCountryRefData = new WithCountryRefData();

   @Test
   @WithReferenceData
   @Sql(value = "classpath:/clear-reference-data.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
   void updateTraining() throws ParseException {
      Long id = repo.save(new Training("Old","OldDesc",null)).getId();

      TrainingDto dto = new TrainingDto();
      dto.name = "newName";
      dto.description = "newDesc";
      dto.startDate = "01-01-2021";
//      dto.teacherId = teacher.getId(); //superclasa
//      dto.teacherId = withCountryRefData.getTeacher().getId(); //extensii JUnit5
      dto.teacherId = 1L;

      service.updateTraining(id, dto);
      Mockito.verify(emailSenderMock).sendScheduleChangedEmail(Mockito.any(), Mockito.any(), Mockito.any());

      Training training = repo.findById(id).get();

      assertThat(training.getName()).isEqualTo("newName");
   }
}