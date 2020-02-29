package victor.training.spring.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.spring.web.domain.Course;
import victor.training.spring.web.domain.User;

public interface UserRepo extends JpaRepository<User, Long> {
    Course getByUsername(String name);
}
