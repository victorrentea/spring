package victor.training.spring.web.repo;

import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.domain.Training;

import java.util.List;

public interface TrainingRepoCustom {
   List<Training> search(TrainingSearchCriteria criteria);
}
