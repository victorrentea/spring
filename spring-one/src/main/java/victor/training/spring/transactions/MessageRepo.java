package victor.training.spring.transactions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {
   @Query("SELECT m FROM Message  m WHERE m.message LIKE concat('%',?1,'%')")
   List<Message> cautaDupaNume(String namePart);

   List<Message> findAllByMessageContaining(String namePart);
   // implementarea generata de spring va deduce JPQLul de executat din NUMELE metodei!


}
