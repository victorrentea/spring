package victor.training.spring.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.spring.web.domain.Training;

public interface TrainingRepo extends JpaRepository<Training, Long> {
    Training getByName(String name);
}
