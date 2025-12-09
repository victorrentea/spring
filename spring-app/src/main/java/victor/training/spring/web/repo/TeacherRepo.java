package victor.training.spring.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.spring.web.entity.ContractType;
import victor.training.spring.web.entity.Teacher;

import java.util.List;

public interface TeacherRepo extends JpaRepository<Teacher, Long> {
    List<Teacher> findByContractType(ContractType contractType);
}
