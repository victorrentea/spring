package victor.training.spring.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import victor.training.spring.web.domain.Training;

public interface TrainingRepo extends JpaRepository<Training, Long>, TrainingRepoCustom {
    Training getByName(String name);

    @Override
    @PreAuthorize("hasAuthority('training.delete') && @securityService.hasAccessOnTraining(#id)")

    void deleteById(Long id);
}
