package victor.training.spring.web.repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import victor.training.spring.web.entity.ProgrammingLanguage;

public interface ProgrammingLanguageRepo extends ReactiveCrudRepository<ProgrammingLanguage, Long> {
}
