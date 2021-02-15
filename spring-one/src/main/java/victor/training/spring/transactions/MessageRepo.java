package victor.training.spring.transactions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {
   List<Message> findByMessageLike(String messagePart);

   @Query("SELECT m From Message m WHERE UPPER(m.message) LIKE UPPER(?1)")
   List<Message> finduMeu(String messagePart);

//   @Query(nativeQuery = true, value = "SELECT m From Message m" +
//                                      " WHERE UPPER(m.message) LIKE UPPER(?1)")
//   List<Message> finduMeuNativ(String messagePart);
}
