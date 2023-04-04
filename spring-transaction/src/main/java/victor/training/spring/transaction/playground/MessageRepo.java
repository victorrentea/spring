package victor.training.spring.transaction.playground;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.annotation.Timed;

public interface MessageRepo extends JpaRepository<Message, Long> {
}
