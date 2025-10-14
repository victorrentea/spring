package victor.training.spring.transaction.playground;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.LockModeType;
import java.util.Optional;

public interface MessageRepo extends JpaRepository<Message, Long> {
  Optional<Message> findByMessageContains(String q);
  // Spring genereaza pt tine SQL prin naming convention

  @Query("""
        SELECT message 
        FROM Message message
        WHERE message.message LIKE '%' || :q || '%' 
        """)// JPQL
  Optional<Message> findDeMana(String q);
//  @Query(value = """
//        SELECT 1
//        FROM MESSAGE message
//        WHERE message.message LIKE '%' || :q || '%'
//        """,nativeQuery = true)// JPQL
//  Optional<Message> findDeMana(String q);
}
