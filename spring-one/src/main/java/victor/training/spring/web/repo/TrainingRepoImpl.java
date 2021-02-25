package victor.training.spring.web.repo;

import lombok.RequiredArgsConstructor;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.domain.Training;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@RequiredArgsConstructor
public class TrainingRepoImpl implements TrainingRepoCustom {
   private final EntityManager em;
   @Override
   public List<Training> search(TrainingSearchCriteria criteria) {
      String jpql = "SELECT t FROM Training t WHERE 1=1";
      Map<String, Object> paramMap = new HashMap<>();


      if (criteria.namePart != null) {
         jpql += "  AND UPPER(t.name) LIKE UPPER('%' || :name || '%') ";
         paramMap.put("name", criteria.namePart);
      }

      if (criteria.teacherId != null) {
         jpql += "   AND t.teacher.id = :teacherId  ";
         paramMap.put("teacherId", criteria.teacherId);
      }

      TypedQuery<Training> query = em.createQuery(jpql, Training.class);

      for (Entry<String, Object> entry : paramMap.entrySet()) {
         query.setParameter(entry.getKey(), entry.getValue());
      }

        return  query.getResultList();
   }
}
