package victor.training.spring.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Long> {
}
