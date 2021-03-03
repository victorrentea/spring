package victor.training.spring.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.spring.web.controller.dto.TrainingDto;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.domain.Training;

import java.util.List;

public interface TrainingRepo extends JpaRepository<Training, Long>, TrainingRepoCustom {

    Training getByName(String name);


}
