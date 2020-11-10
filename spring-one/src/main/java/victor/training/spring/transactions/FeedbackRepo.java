package victor.training.spring.transactions;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedbackRepo extends JpaRepository<Feedback, Long>, FeedbackRepoCustom {

   List<Feedback> findByMessageLike(String part);

   @Query("select f from Feedback f \n" +
          "inner join " +
          "Message m ON f.message = m.message \n" +
          "WHERE m.id=:messageId")
   List<Feedback> findByMessageId(Long messageId);

   @Query(value = "select 1 from DUAL", nativeQuery = true)
   int fromDual();



//   List<Feedback> findBy(Specification<Feedback> feedbackSpecification);


//   List<Feedback> findByScore
}
