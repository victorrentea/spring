package victor.training.spring.web.repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import victor.training.spring.web.entity.Training;

public interface TrainingRepo extends ReactiveCrudRepository<Training, Long> {
    Mono<Training> getByName(String name);
}
