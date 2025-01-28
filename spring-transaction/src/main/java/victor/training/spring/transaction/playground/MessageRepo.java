package victor.training.spring.transaction.playground;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.LockModeType;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MessageRepo extends JpaRepository<Message, Long> {
  @Query("FROM Message WHERE id = ?1")
  @Lock(LockModeType.PESSIMISTIC_WRITE) // db row lock via "SELECT .. FOR UPDATE"
  // https://stackoverflow.com/questions/33062635/difference-between-lockmodetype-jpa
  Optional<Message> findByIdLocking(long id);

  @Transactional
  @Modifying
  @Query(value = "/*+ */insert into MESSAGE(id, message) values (42, ?1)",
      nativeQuery = true)
  void insert(String name);

  // o metoda fara implementare, care va fi implementata de Spring Data JPA
  // asta merge pentru ca metoda se numeste findByMessage
  // si in clasa Message avem un camp message
  // Spring Data JPA va face automat query-ul
//  Message findByMessageAndId(@NotNull String message, Long id);

}
