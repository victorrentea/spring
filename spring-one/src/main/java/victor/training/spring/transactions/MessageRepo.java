package victor.training.spring.transactions;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Long> {
//   int countAllByIdBefore1000
}
