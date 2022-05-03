package victor.training.spring.transaction.exercises.exceptions;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExceptionalEntityRepo extends JpaRepository<ExceptionalEntity, Long> {
}
