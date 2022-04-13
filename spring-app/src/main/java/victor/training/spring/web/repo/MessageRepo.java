package victor.training.spring.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import victor.training.spring.web.entity.Message;

public interface MessageRepo extends JpaRepository<Message, Long> {
   @Query(value = "SELECT COUNT(*) FROM MESSAGE WHERE MESSAGE=?1", nativeQuery = true)
   int countByName(String name);
}
