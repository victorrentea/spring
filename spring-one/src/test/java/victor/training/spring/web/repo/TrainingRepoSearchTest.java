package victor.training.spring.web.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.domain.Teacher;
import victor.training.spring.web.domain.Training;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(scripts = "classpath:cleanup.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface CleanupData {

}


@SpringBootTest
//@RunWith(SpringRunner.class) // pt junit4
@ActiveProfiles({"db-mem","test"})
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
//@CleanupData
class TrainingRepoSearchTest {
   @Autowired
   private TrainingRepo trainingRepo;
   @Autowired
   private TeacherRepo teacherRepo;

//   @BeforeEach
//   public void method() {
//      trainingRepo.deleteAll();
//      teacherRepo.deleteAll();
//   }
   @Test
   public void withoutCriteria() {
      trainingRepo.save(new Training("Spring"));
      TrainingSearchCriteria criteria = new TrainingSearchCriteria();
      List<Training> list = trainingRepo.search(criteria);

      assertThat(list).hasSize(1);
   }
   @Test
   public void byTeacher() {
      // given
      Teacher teacher = new Teacher();
      teacherRepo.save(teacher);
      trainingRepo.save(new Training().setTeacher(teacher));

      // when
      TrainingSearchCriteria criteria = new TrainingSearchCriteria();

      criteria.teacherId = teacher.getId();
      assertThat(trainingRepo.search(criteria)).hasSize(1);

      criteria.teacherId = -1L;
      assertThat(trainingRepo.search(criteria)).isEmpty();
   }
}