package victor.training.spring.repo;

import java.util.List;

public interface CustomerRepoCustom {
   List<Customer> search(CustomerSearchCriteria criteria);
}
