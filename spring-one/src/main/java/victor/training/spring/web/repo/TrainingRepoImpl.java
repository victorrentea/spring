package victor.training.spring.web.repo;

import lombok.RequiredArgsConstructor;
import victor.training.spring.web.controller.dto.TrainingSearchCriteria;
import victor.training.spring.web.domain.Training;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@RequiredArgsConstructor
public class TrainingRepoImpl implements TrainingRepoCustom {
   private final EntityManager em;
   @Override
   public List<Training> search(TrainingSearchCriteria criteria) {
        TypedQuery<Training> query = em.createQuery(
            "SELECT t FROM Training t WHERE 1=1 ", Training.class);


        return  query.getResultList();
   }
}
