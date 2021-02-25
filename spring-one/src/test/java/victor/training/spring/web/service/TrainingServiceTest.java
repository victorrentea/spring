package victor.training.spring.web.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.domain.Teacher;
import victor.training.spring.web.domain.Training;
import victor.training.spring.web.repo.TeacherRepo;
import victor.training.spring.web.repo.TrainingRepo;

import java.text.ParseException;

@SpringBootTest
@ActiveProfiles("db-mem")
@Transactional
class TrainingServiceTest {
   @Autowired
   private TrainingService service;
   @Autowired
   private TrainingRepo repo;

   @MockBean
   private EmailSender emailSenderMock;

   @Autowired
   private TeacherRepo teacherRepo;

   @Test
   void updateTraining() throws ParseException {
      Teacher teacher = new Teacher();
      Long teacherId = teacherRepo.save(teacher).getId();
      Long id = repo.save(new Training("Old","OldDesc",null)).getId();


      TrainingDto dto = new TrainingDto();
      dto.name = "newName";
      dto.description = "newDesc";
      dto.startDate = "01-01-2021";
      dto.teacherId = teacherId;

      service.updateTraining(id, dto);
      Mockito.verify(emailSenderMock).sendScheduleChangedEmail(Mockito.any(), Mockito.any(), Mockito.any());
   }
}