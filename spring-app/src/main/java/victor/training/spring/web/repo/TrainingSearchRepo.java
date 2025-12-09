package victor.training.spring.web.repo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.entity.Training;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class TrainingSearchRepo {
  private final EntityManager entityManager;

  public List<Training> search(TrainingSearchCriteria searchCriteria) {
    List<String> jpqlParts = new ArrayList<>();
    jpqlParts.add("SELECT t FROM Training t WHERE 1=1");
    Map<String, Object> params = new HashMap<>();

    if (searchCriteria.name != null) {
      jpqlParts.add("AND UPPER(t.name) LIKE UPPER('%' || :name || '%')");
      params.put("name", searchCriteria.name);
    }

    if (searchCriteria.teacherId != null) {
      jpqlParts.add("AND t.teacher.id = :teacherId");
      params.put("teacherId", searchCriteria.teacherId);
    }

    TypedQuery<Training> query = entityManager.createQuery(String.join("\n", jpqlParts), Training.class);
    for (String param : params.keySet()) {
      query.setParameter(param, params.get(param));
    }
    return query.getResultList();
  }

}
