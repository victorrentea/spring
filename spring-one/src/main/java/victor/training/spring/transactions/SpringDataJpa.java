package victor.training.spring.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SpringDataJpa {
   private final FeedbackRepo repo;
   private final MessageRepo messageRepo;
   @Transactional
   public void play() {
      messageRepo.save(new Message("Varza"));
      Feedback feedback = new Feedback();
      feedback.setMessage("Varza");
      repo.save(feedback);

      System.out.println(repo.findByMessageLike("%arz%"));
      System.out.println(repo.findByMessageId(1L));


//      Specification.where()

      System.out.println(repo.manualSearch());

   }
}
