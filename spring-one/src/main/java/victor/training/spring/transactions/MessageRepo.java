package victor.training.spring.transactions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {
   @Query("SELECT m FROM Message m WHERE m.message = ?1")
   List<Message> findWithMessage(String message);

   List<Message> findByMessageLike(String part);

   // ce mai stie sa faca Spring Data Jpa
  // query-uri paginate. sa iti dea Iterator/Stream<Entity>
   // queryuri native sau stored procedures
   // sa iti dea Optional empty daca nu gasesti
}
