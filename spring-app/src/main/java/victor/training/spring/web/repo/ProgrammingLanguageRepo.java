package victor.training.spring.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.spring.web.entity.ProgrammingLanguage;

import java.util.Collection;

public interface ProgrammingLanguageRepo extends JpaRepository<ProgrammingLanguage, Long> {
//  Collection<Object> findAllByTrainingId(Long trainingId);
}
