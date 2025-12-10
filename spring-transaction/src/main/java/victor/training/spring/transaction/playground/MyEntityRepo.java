package victor.training.spring.transaction.playground;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MyEntityRepo extends JpaRepository<MyEntity, Long> {
  @Query("FROM MyEntity WHERE id = ?1")
  @Lock(LockModeType.PESSIMISTIC_WRITE)
    // db row lock via "SELECT .. FOR UPDATE"
    // https://stackoverflow.com/questions/33062635/difference-between-lockmodetype-jpa
  Optional<MyEntity> findByIdLocking(long id);

  List<MyEntity> findByName(String name);

  @Query("""
          SELECT e 
          FROM MyEntity e
          LEFT JOIN FETCH e.tags
          """)
  List<MyEntity> findAllCuCopchii();
}
