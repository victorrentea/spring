package victor.training.spring.web.repo;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.context.SecurityContextHolder;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.domain.Training;
import victor.training.spring.web.security.SecurityUser;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TrainingRepoImpl implements TrainingRepoCustom {
   private final EntityManager entityManager;
   @Override
   public List<Training> search(TrainingSearchCriteria criteria) {
      SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      String visibleTeacherIdCsv = securityUser.getManagedTeacherIds().stream().map(Objects::toString).collect(Collectors.joining(","));
      // DON'T DO THIS: JPQL injection!
      String jpql = "SELECT t FROM Training t " +
                    "WHERE t.teacher.id IN (" + visibleTeacherIdCsv + ") ";
//      jpql += " AND t.name = '" + criteria.name + "'";
      if (Strings.isNotBlank(criteria.name)) {
      jpql += " AND t.name = :criteriaName ";

      }
//      jpql += " AND t.name = ' OR '1'='1'";

       String sql =  " SELECT ***, FORM WHERE ORDER BY " + criteria.colValue; // colValue vine asa: ID; TRUNCATE USERS;

      TypedQuery<Training> query = entityManager.createQuery(jpql, Training.class);
      if (Strings.isNotBlank(criteria.name)) {
         query.setParameter("criteriaName", criteria.name);
      }
      return query.getResultList();
   }
}
