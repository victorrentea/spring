package victor.training.spring.transactions;

import java.util.Collections;
import java.util.List;

public class FeedbackRepoImpl implements FeedbackRepoCustom {

   @Override
   public List<Feedback> manualSearch() {
      return Collections.emptyList();
   }
}
