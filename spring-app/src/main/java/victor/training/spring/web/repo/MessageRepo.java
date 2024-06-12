package victor.training.spring.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.spring.web.entity.Message;

import java.util.List;
import java.util.stream.Stream;

public interface MessageRepo extends JpaRepository<Message, Long> {

//  Stream<Message> findAll2();
}
