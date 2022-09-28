package victor.training.spring.web.repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import victor.training.spring.web.entity.Message;

public interface MessageRepo extends ReactiveCrudRepository<Message, Long> {
}
