package victor.training.spring.transactions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.stream.Stream;

public interface MessageRepo extends JpaRepository<Message, Long> {

   Message findByMessage(String message);

   @Query("SELECT m from Message m LEFT JOIN FETCH m.tags WHERE m.id=?1" )
   Message findWithTags(Long id);

   @Query("FROM Message ")
   Stream<Message> streamAll();
}
