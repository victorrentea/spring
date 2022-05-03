package victor.training.spring.transaction.exercises.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

// TODO fix tests
@SpringBootApplication
@RequiredArgsConstructor
public class JpaExercises {

   private final JpaEntityRepo repo;

   public void changesToEntityAreAutoSaved(long id) {
      JpaEntity entity = repo.findById(id).orElseThrow();
      // TODO change name to 'changed' without calling save
      // Hint: entities updated withing a @Transactional method are auto-saved
   }

   public void lazyLoadingRequiresSurroundingTransaction(long id) {
      JpaEntity entity = repo.findById(id).orElseThrow();
      // TODO print all entity.tags, comma separated
      // Hint: keep the transaction open between find and accessing children
   }

   @Transactional
   public void prematureAutoFlushing() {
      repo.save(new JpaEntity("new",18));
      // TODO cause the 1st level cache to be auto-flushed before the next line - try as many ways
      System.out.println("END OF METHOD"); // DO NOT CHANGE
   }

   public boolean firstLevelCache(Long id) {
      JpaEntity one = repo.findById(id).orElseThrow();
      JpaEntity two = repo.findById(id).orElseThrow();
      // TODO when do you get the same object?
      return one == two;
   }
}
