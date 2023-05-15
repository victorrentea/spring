package victor.training.spring.transaction.exercises.exceptions;

import org.springframework.data.jpa.repository.JpaRepository;

// @Repository nu e necesar aici
public interface ExceptionalEntityRepo extends JpaRepository<ExceptionalEntity, Long> {
}
