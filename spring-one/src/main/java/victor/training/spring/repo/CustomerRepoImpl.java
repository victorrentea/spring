package victor.training.spring.repo;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class CustomerRepoImpl implements CustomerRepoCustom {
   private final EntityManager em;

   @Override
   public List<Customer> search(CustomerSearchCriteria criteria) {

//      String jpql = "SELECT c FROM Customer c WHERE 1=1 ";
//      if () {
//         jpql += "    AND c.name=:name    ";
//      }
//      entityManager.createQuery(jqpl);
      return null;
   }
}
