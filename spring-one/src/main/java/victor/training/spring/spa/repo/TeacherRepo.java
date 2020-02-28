package victor.training.spring.spa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.spring.spa.domain.Course;
import victor.training.spring.spa.domain.Teacher;

public interface TeacherRepo extends JpaRepository<Teacher, Long> {
}
