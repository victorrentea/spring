package victor.training.spring.web.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import victor.training.spring.web.entity.ContractType;
import victor.training.spring.web.entity.Teacher;

import java.util.List;

public interface TeacherRepo extends ReactiveCrudRepository<Teacher, Long> {
}
