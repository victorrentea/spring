package victor.training.spring.web.repo;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.domain.Teacher;
import victor.training.spring.web.domain.Training;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("db-mem")
@Transactional
//@RunWith(SpringRunner.class) // junit4
class TrainingRepoImplTest {
   @Autowired
   TrainingRepo repo;
//   @Autowired
//   VreunServiciu fereste;
Teacher teacher = new Teacher();
   TrainingSearchCriteria criteria = new TrainingSearchCriteria();


   @BeforeEach
   public void initialize() {
      System.out.println("#sieu!");
   }

   @Test
   public void noCriteria() {
      repo.save(new Training("T",null, null));
      List<Training> list = repo.search(new TrainingSearchCriteria());
      assertEquals(1, list.size());
   }


   @Test
   public void byTeacherId() {
      Training training = new Training("T", null, null);
      training.setTeacher(teacher);
      repo.save(training);

      criteria.teacherId = teacher.getId();
      assertThat(repo.search(criteria)).hasSize(1);
   }
   @Test
   public void byTeacherIdNoMatch() {
      Training training = new Training("T", null, null);
      training.setTeacher(teacher);
      repo.save(training);

      criteria.teacherId = -1L;

      assertThat(repo.search(criteria)).isEmpty();
   }

   @Test
   public void byName() {
      Training training = new Training("JpXA", null, null);
      repo.save(training);

      criteria.namePart = "Px";

      assertThat(repo.search(criteria)).hasSize(1);
   }
   @Test
   public void byNameNoMatch() {
      Training training = new Training("JpXA", null, null);
      repo.save(training);

      criteria.namePart = "Copac";

      assertThat(repo.search(criteria)).isEmpty();
   }

}

//@Service
//@RequiredArgsConstructor
//class VreunServiciu {
//   private final TrainingRepo repo;
//
//   @Transactional(propagation = Propagation.REQUIRES_NEW)
//   public void Doamne() {
//      repo.save(new Training("Pentru ca Pot",null, null));
//
//   }
//}