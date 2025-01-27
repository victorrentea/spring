package victor.training.spring.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import victor.training.spring.web.entity.Message;

public interface MessageRepo extends JpaRepository<Message, Long> {
}
