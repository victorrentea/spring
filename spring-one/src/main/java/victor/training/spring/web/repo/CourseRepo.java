package victor.training.spring.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.spring.web.domain.Course;

public interface CourseRepo extends JpaRepository<Course, Long> {
    Course getByName(String name);
}
