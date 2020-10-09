package victor.training.spring.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import victor.training.spring.web.domain.Supplier;

public interface SupplierRepo extends JpaRepository<Supplier, Long> {
   Supplier findByName(String supplierName);
}
