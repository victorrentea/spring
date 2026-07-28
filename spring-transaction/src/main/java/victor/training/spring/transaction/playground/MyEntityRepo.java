package victor.training.spring.transaction.playground;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.Optional;

public interface MyEntityRepo extends JpaRepository<MyEntity, Long> {
  @Query(value = """
      insert into MY_ENTITY(id, name) values (100,?)
      """, nativeQuery = true)
  @Modifying
  void nativa(String name);

//  @Procedure pt apel de PL/SQL

  @Query("FROM MyEntity WHERE id = ?1")
  @Lock(LockModeType.PESSIMISTIC_WRITE) // row lock via SELECT..  FOR UPDATE
  Optional<MyEntity> lock(long id);

}
