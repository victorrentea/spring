package victor.training.spring.transaction.exercises.jpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import victor.training.spring.transaction.test.tools.CaptureSystemOutput;
import victor.training.spring.transaction.test.tools.CaptureSystemOutput.OutputCapture;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JpaExercisesTest {
   @Autowired
   JpaExercises jpaExercises;
   @Autowired
   JpaEntityRepo repo;

   Long id;

   @BeforeEach
   final void before() {
      repo.deleteAll(); // explicit cleanup as tests cannot be @Transactional here
      id = repo.save(new JpaEntity("name", 18, "tag1", "tag2")).getId();
   }

   @Test
   void changesToEntityAreAutoSaved() {
      jpaExercises.changesToEntityAreAutoSaved(id);

      assertThat(repo.findById(id).orElseThrow().getName()).isNotEqualTo("name");
   }

   @Test
   @CaptureSystemOutput
   void lazyLoadingRequiresSurroundingTransaction(OutputCapture capture) {
      jpaExercises.lazyLoadingRequiresSurroundingTransaction(id);

      assertThat(capture.toString()).contains("tag1,tag2");
   }

   @Test
   @CaptureSystemOutput
   void prematureAutoFlushing(OutputCapture capture) {
      jpaExercises.prematureAutoFlushing();

      assertThat(capture.toString()).containsSubsequence(List.of("insert", "END OF METHOD"));
   }

   @Test
   void firstLevelCache() {
      boolean r = jpaExercises.firstLevelCache(id);

      assertThat(r).isTrue();
   }
}
