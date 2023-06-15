package victor.training.spring.transaction.playground;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {
  @Modifying
  @Transactional
  @Query(value = "insert into MESSAGE(id, message) values ( 100, ?1)" ,nativeQuery = true)
  void suchili(String name);

  // LIKE SELECT * FROM MESSAGE m WHERE m.MESSAGE LIKE ?
  List<Message> findByMessageLike(String part); // query derivat din numele metodei

  List<Message> findByMessageContainingIgnoreCase(String part);

  @Query("SELECT m FROM Message m WHERE m.message LIKE '%' || ?1 || '%'") // JPQL
  Message alMano(String part);
}
