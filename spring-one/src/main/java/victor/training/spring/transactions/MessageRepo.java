package victor.training.spring.transactions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {
   @Query("SELECT m FROM Message m WHERE m.message = ?1")
   List<Message> findWithMessage(String message);
}
