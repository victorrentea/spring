package victor.training.spring.transactions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepo extends JpaRepository<Message, Long> {
   @Query(value = "INSERT INTO MESSAGE(ID,MESSAGE) VALUES (616, 'aaa')", nativeQuery = true)
   void someNative();
//   int countAllByIdBefore1000
}
