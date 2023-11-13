package victor.training.spring.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import victor.training.spring.web.entity.Training;

import java.util.Optional;

public interface TrainingRepo extends JpaRepository<Training, Long>, JpaSpecificationExecutor<Training> {
    Optional<Training> findById(Long aLong);

    // SPring stie automat sa genereze un SELECT in DB WHERE NAME =
    Training getByName(String name);

//    @Query("jqpl")
    int countByNameAndIdNot(String name, Long currentId);
}
