package victor.training.spring.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.spring.web.entity.Message;

public interface MessageRepo extends JpaRepository<Message, Long> {

}
