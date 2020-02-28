package victor.training.spring.spa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.spring.spa.domain.Course;

public interface CourseRepo extends JpaRepository<Course, Long> {
    Course getByName(String name);
}
