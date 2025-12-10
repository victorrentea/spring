package victor.training.spring.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import victor.training.spring.web.entity.Training;

import java.util.Optional;
import java.util.stream.Stream;

public interface TrainingRepo extends JpaRepository<Training, Long>, JpaSpecificationExecutor<Training> {
    Optional<Training> findById(Long aLong);

    Training getByName(String name);

    int countByNameAndIdNot(String name, Long currentId);
}
