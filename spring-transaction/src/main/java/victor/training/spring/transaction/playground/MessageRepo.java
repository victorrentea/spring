package victor.training.spring.transaction.playground;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepo extends JpaRepository<Message, Long> {

//  @Query("jpql")
//  @Query(value = "sql", nativeQuery = true)
  Message findByMessageContainingIgnoreCase(String text);
  // I want a exception to be thrown by this method instead of returing a null

  // return Optional<> if they could return nothing
  // throw exception instead of returning a null if they don't declare to return Optional
}
