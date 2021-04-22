package victor.training.spring.transactions;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Long> {

   Message findByMessage(String message);
}
