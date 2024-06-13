package victor.training.spring.transaction.playground;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.LockModeType;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//@Repository nu e necesar
// springu genereaza dinamic o implementare a acestei interfete la startup
public interface MessageRepo extends JpaRepository<Message, Long> {
  //#1💖💖💖 @Query autogenerate face el automat din numele metodei asteia, sa-mi bag picioru
  Optional<Message> findByMessageContainingIgnoreCaseOrderByMessageDesc(String message);

  // Query explicite
  //  #2 JPA: 💖💖
  //  @Query("SELECT m FROM Message m WHERE m.message LIKE %?1%") // JPQL ~ SQL

// #3 SQL nativ
// @Query(value = "SELECT m FROM MESSAGE m WHERE m.message LIKE '%' || ? || '1%'", nativeQuery = true) // SQL
//  @Query(value = """
//      SELECT m FROM Message m
//      O PAGINA INTREAGA A4
//      WHERE m.message LIKE %?1%
//      """, nativeQuery = true) // mai bine decat JdbcTemplate
//  Optional<Message> findByMessageLike(String message);


  @Query("FROM Message WHERE id = ?1")
  @Lock(LockModeType.PESSIMISTIC_WRITE) // db row lock via "SELECT .. FOR UPDATE"
  // https://stackoverflow.com/questions/33062635/difference-between-lockmodetype-jpa
  Optional<Message> findByIdLocking(long id);

}
