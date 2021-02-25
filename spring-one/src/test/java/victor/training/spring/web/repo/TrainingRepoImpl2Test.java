package victor.training.spring.web.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.domain.Training;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("db-mem")//////aici
@Transactional
class TrainingRepoImpl2Test {
   @Autowired
   TrainingRepo repo;

   @BeforeEach
   public void initialize() {
      System.out.println("#sieu!");
      assertEquals(0, repo.count()); // checks that the pre-existing table is empty - ca ma bazez pe asta mai jos in teste.
   }

//   @DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
   @Test
   public void noCriteriaBis() {
      repo.save(new Training("P", "D", null));
      List<Training> list = repo.search(new TrainingSearchCriteria());
      assertThat(list).hasSize(1);
   }
}
