package victor.training.spring.web.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.domain.Teacher;
import victor.training.spring.web.domain.Training;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//@RunWith(SpringRunner.class) // pt junit4
@ActiveProfiles({"db-mem","test"})
class TrainingRepoSearchTest {
   @Autowired
   private TrainingRepo trainingRepo;
   @Autowired
   private TeacherRepo teacherRepo;

   @BeforeEach
   public void method() {
      teacherRepo.deleteAll();
      trainingRepo.deleteAll();
   }
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