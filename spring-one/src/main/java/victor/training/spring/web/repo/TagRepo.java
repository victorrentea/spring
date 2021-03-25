package victor.training.spring.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import victor.training.spring.web.domain.Tag;

import java.util.List;

public interface TagRepo extends JpaRepository<Tag, Long> {
   @Query("SELECT tag FROM Training t LEFT join t.tags tag WHERE t.id = ?1")
   List<Tag> findAllByTraining(long trainingId);
}
