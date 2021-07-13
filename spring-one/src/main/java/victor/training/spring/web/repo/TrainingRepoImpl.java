package victor.training.spring.web.repo;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.domain.Training;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class TrainingRepoImpl implements TrainingRepoCustom {
   private final EntityManager em;
   @Override
   public List<Training> search(TrainingSearchCriteria criteria) {
      String jpql = "SELECT t FROM Training t WHERE 1 = 1 ";
      Map<String, Object> paramsMap = new HashMap<>();
      if (StringUtils.isNotBlank(criteria.name)) {
         jpql += " AND t.name = :name ";
         paramsMap.put("name", criteria.name);
      }
      if (criteria.teacherId != null) {
         jpql += " AND t.teacher.id = :teacherId ";
         paramsMap.put("teacherId", criteria.teacherId);
      }

      TypedQuery<Training> query = em.createQuery(jpql, Training.class);
      for (String paramName : paramsMap.keySet()) {
         query.setParameter(paramName, paramsMap.get(paramName));
      }
      return query.getResultList();
   }
}
