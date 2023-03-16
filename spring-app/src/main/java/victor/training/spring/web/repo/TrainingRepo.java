package victor.training.spring.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import victor.training.spring.web.entity.Training;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface TrainingRepo extends JpaRepository<Training, Long>, JpaSpecificationExecutor<Training> {
    Optional<Training> findById(Long aLong);

    Training getByName(String name);
    Training getByNameAndIdNot(String name, Long currentId);
}
