package victor.training.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Long>, CustomerRepoCustom {
   List<Customer> findByName(String name);
   Optional<Customer> findByNameLike(String namePart);


   @Query("SELECT c\n" +
          "FROM Customer c\n" +
          "WHERE UPPER(c.name) LIKE UPPER('%' || ?1 || '%')\n")
   List<Customer> findByNamePart(String namePart);
}
