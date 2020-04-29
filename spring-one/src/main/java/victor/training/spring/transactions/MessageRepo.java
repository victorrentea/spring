package victor.training.spring.transactions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MessageRepo extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE m.id = 1")
    Message metodaTare();

    Optional<Message> findByMessage(String searchedMessage);
}
