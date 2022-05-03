package victor.training.spring.transaction.exercises.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEntityRepo extends JpaRepository<JpaEntity, Long> {
}
