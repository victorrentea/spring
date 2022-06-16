package victor.training.spring.transaction.playground;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepo extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m LEFT JOIN FETCH m.tags WHERE m.id=?1")
    Message findCuTaguri(long l);

}
