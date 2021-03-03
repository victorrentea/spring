package victor.training.spring.web.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.domain.Teacher;
import victor.training.spring.web.domain.Training;
import victor.training.spring.web.service.KafkaClient;
import victor.training.spring.web.service.TeacherService;
import victor.training.spring.web.service.TrainingService;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@Sql(scripts = "classpath:cleanup.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface CleanupData {

}


class TrainingRepoSearchTest extends RepoTestBase {
   @Autowired
   private TrainingRepo trainingRepo;
   @Autowired
   private TrainingService trainingService;
   @Autowired
   private TeacherRepo teacherRepo;

   @MockBean
   KafkaClient kafkaClient;


   @BeforeEach
   public void assumptions() {
      assertThat(trainingRepo.findAll()).isEmpty();
//      trainingRepo.deleteAll();
//      teacherRepo.deleteAll();
   }
   @Test
   public void withoutCriteria() {
      trainingRepo.save(new Training("Spring"));
      TrainingSearchCriteria criteria = new TrainingSearchCriteria();
      List<TrainingDto> list = trainingService.search(criteria);

      verify(kafkaClient).logSearch(any());
      assertThat(list).hasSize(1);
   }
   @Test
   public void byTeacher() {
      // given
      Teacher teacher = new Teacher();
      teacherRepo.save(teacher);
      trainingRepo.save(new Training().setTeacher(teacher).createdBy("user"));

      // when
      TrainingSearchCriteria criteria = new TrainingSearchCriteria();

      criteria.teacherId = teacher.getId();
      assertThat(trainingService.search(criteria)).hasSize(1);

      criteria.teacherId = -1L;
      assertThat(trainingRepo.search(criteria)).isEmpty();
   }
}