package victor.training.spring.transactions;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {

//   {
//      EntityManager em;
//      em.createNamedQuery("ciocan.rosu").getResultList();
//   }

   @Query("SELECT m FROM Message  m WHERE m.message LIKE concat('%',?1,'%')")
   List<Message> cautaDupaNume(String namePart);

   Page<Message> findAllByMessageContaining(String namePart, Pageable page);

   @Modifying(flushAutomatically = true, clearAutomatically = true)
   @Procedure(name = "MY_PACK.MY_PROC")
   void procCall();

   @Query(value = "select count(*) from MESSAGE",nativeQuery = true)
   int countNativ();
   // implementarea generata de spring va deduce JPQLul de executat din NUMELE metodei!


}
