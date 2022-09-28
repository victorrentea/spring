package victor.training.spring.web.repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import victor.training.spring.web.entity.User;

public interface UserRepo extends ReactiveCrudRepository<User, Long> {

}
