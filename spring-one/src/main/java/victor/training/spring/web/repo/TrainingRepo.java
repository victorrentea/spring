package victor.training.spring.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import victor.training.spring.web.domain.Training;

import java.util.Optional;

public interface TrainingRepo
    extends JpaRepository<Training, Long>,
            TrainingRepoCustom {
    @Query("SELECT t FROM Training t WHERE t.name = :name ")
    Optional<Training> getByName(String name);
}