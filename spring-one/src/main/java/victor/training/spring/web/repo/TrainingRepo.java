package victor.training.spring.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.spring.web.domain.Training;

import java.util.List;
import java.util.Set;

public interface TrainingRepo extends JpaRepository<Training, Long> {
    // @Query("SELECT t FROM Training t WHERE t.teacher.id IN (?)")
    List<Training> findByTeacherIdIn(Set<Long> teacherIds);

    Training getByName(String name);
}
