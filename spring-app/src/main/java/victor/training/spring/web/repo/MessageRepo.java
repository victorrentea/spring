package victor.training.spring.web.repo;

import io.micrometer.core.annotation.Timed;
import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.spring.web.entity.Message;

@Timed
public interface MessageRepo extends JpaRepository<Message, Long> {
}
