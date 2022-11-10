package victor.training.spring.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import victor.training.spring.web.entity.Training;

public interface TrainingRepo extends JpaRepository<Training, Long>, JpaSpecificationExecutor<Training> {
    boolean existsByName(String name);
    //@Query("SELECT 1 FROM Training where name = ?1 AND id != ?2
    boolean existsByNameAndIdNot(String newName, Long updatedId);
}
