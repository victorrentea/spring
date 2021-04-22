package victor.training.spring.transactions;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Long> {

   Message findByMessage(String message);
//   @Query("SELECT m from Message m LEFT JOIN FETCH m.message WHERE m.id=?1" )
//   Message findWithTags(Long id);

}
