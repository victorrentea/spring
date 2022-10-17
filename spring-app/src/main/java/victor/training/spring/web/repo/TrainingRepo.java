package victor.training.spring.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import victor.training.spring.web.entity.Training;

import java.util.List;

public interface TrainingRepo extends JpaRepository<Training, Long>, JpaSpecificationExecutor<Training> {
    Training getByName(String name);

    @Query("SELECT t FROM Training t" +
           " LEFT JOIN FETCH t.programmingLanguage" +
           " LEFT JOIN FETCH t.teacher")
    List<Training> findDedicat();
}
