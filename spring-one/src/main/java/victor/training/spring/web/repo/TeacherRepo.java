package victor.training.spring.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.spring.web.domain.Teacher;

public interface TeacherRepo extends JpaRepository<Teacher, Long> {
}
