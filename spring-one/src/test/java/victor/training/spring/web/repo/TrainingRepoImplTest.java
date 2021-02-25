package victor.training.spring.web.repo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
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

   @Test
   public void noCriteria() {
      repo.save(new Training("T","D", null));
      List<Training> list = repo.search(new TrainingSearchCriteria());
      assertEquals(1, list.size());
//      repo.deleteAll();
   }

   @Test
   public void noCriteriaBis() {
      repo.save(new Training("P","D", null));
      List<Training> list = repo.search(new TrainingSearchCriteria());
      assertThat(list).hasSize(1);
//      repo.deleteAll();
   }
}