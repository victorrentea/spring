package victor.training.spring.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import victor.training.spring.web.entity.ContractType;
import victor.training.spring.web.entity.Teacher;

import java.util.List;

public interface TeacherRepo extends JpaRepository<Teacher, Long> {
   List<Teacher> findByContractType(ContractType contractType);

   @Query("SELECT te FROM Teacher te" +
          " LEFT JOIN FETCH te.trainings tr" 
//          " LEFT JOIN FETCH tr.programmingLanguage"
   )
   List<Teacher> findAllWithChildren();
}
