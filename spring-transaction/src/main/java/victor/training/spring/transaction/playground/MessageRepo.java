package victor.training.spring.transaction.playground;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepo
        extends JpaRepository<Message, Long> {

    List<Message> findAllByMessage(String message);
}
