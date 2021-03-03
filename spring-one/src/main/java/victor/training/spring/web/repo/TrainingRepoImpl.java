package victor.training.spring.web.repo;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.domain.Training;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class TrainingRepoImpl implements TrainingRepoCustom {
   private final EntityManager entityManager;

   @Override
   public List<Training> search(TrainingSearchCriteria criteria) {
      Map<String, Object> params = new HashMap<>();
      String jpql = "SELECT t FROM Training t WHERE 1=1 ";

      if (StringUtils.isNotBlank(criteria.name)) {
          jpql += " AND t.name = :name";
          params.put("name", criteria.name);
      }
      if (criteria.teacherId != null) {
          jpql += " AND t.teacher.id = :teacherId";
          params.put("teacherId", criteria.teacherId);
      }
      TypedQuery<Training> query = entityManager.createQuery(jpql, Training.class);
      for (String key : params.keySet()) {
          query.setParameter(key, params.get(key));
      }
      List<Training> resultList = query.getResultList();

//      other.lasaGunoi();
      return resultList;
   }
   private final Other other;
}

@Component
@RequiredArgsConstructor
class Other {
   private final EntityManager entityManager;
   @Transactional(propagation = Propagation.REQUIRES_NEW)
   public void lasaGunoi() {
      entityManager.persist(new Training("Gunoi"));
   }
}