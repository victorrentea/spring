package victor.training.spring.transaction.playground;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo
        extends JpaRepository<Message, Long> {
}
