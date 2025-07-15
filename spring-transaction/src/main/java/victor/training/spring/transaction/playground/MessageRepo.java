package victor.training.spring.transaction.playground;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.LockModeType;
import java.util.Optional;

public interface MessageRepo extends JpaRepository<Message, Long> {




  @Query(nativeQuery = true,
      value = "insert into MESSAGE(id, message) values (1,?1 )")
  void f(String s);

  @Query("FROM Message WHERE id = ?1")
  @Lock(LockModeType.PESSIMISTIC_WRITE) // db row lock via "SELECT .. FOR UPDATE"
  // https://stackoverflow.com/questions/33062635/difference-between-lockmodetype-jpa
  Optional<Message> findByIdLocking(long id);

  @Query("""
    SELECT m 
    FROM Message m
    LEFT JOIN FETCH m.tags 
    WHERE m.id=?1
    """) // daca vezi SELECTuri succesive pe acelasi flux
  // poti incerca LEFT JOIN FETCH sa aduci tot ce ai nevoie intr-un singur query
  Optional<Message> findByIdCuCopii(long id);
}
