package victor.training.spring.web.repo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.domain.Training;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@RunWith(SpringRunner.class) // pt junit4
@ActiveProfiles({"db-mem","test"})
class TrainingRepoTest {
   @Autowired
   private TrainingRepo repo;

   @BeforeEach
   public void method() {
      repo.deleteAll();
   }
   @Test
   public void search() {
      repo.save(new Training("Spring"));
      TrainingSearchCriteria criteria = new TrainingSearchCriteria();
      List<Training> list = repo.search(criteria);

      Assertions.assertThat(list).hasSize(1);
   }
   @Test
   public void search2() {
      repo.save(new Training("Spring"));
      TrainingSearchCriteria criteria = new TrainingSearchCriteria();
      List<Training> list = repo.search(criteria);

      Assertions.assertThat(list).hasSize(1);
   }
}