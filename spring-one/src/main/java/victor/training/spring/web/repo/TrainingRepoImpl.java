package victor.training.spring.web.repo;

import lombok.RequiredArgsConstructor;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.domain.Training;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@RequiredArgsConstructor
public class TrainingRepoImpl implements TrainingRepoCustom {
   private final EntityManager entityManager;
   @Override
   public List<Training> search(TrainingSearchCriteria criteria) {
      String visibleTeacherIdCsv = "1"; // TODO
      // DON'T DO THIS: JPQL injection!
      String jpql = "SELECT t FROM Training t " +
                    "WHERE t.teacher.id IN (" + visibleTeacherIdCsv + ")";
      jpql += " AND t.name = '" + criteria.name + "'";

      TypedQuery<Training> query = entityManager.createQuery(jpql, Training.class);

      return query.getResultList();
   }
}
